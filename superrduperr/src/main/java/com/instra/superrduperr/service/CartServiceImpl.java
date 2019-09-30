package com.instra.superrduperr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.instra.superrduperr.dao.bean.Carts;
import com.instra.superrduperr.dao.bean.Items;
import com.instra.superrduperr.exception.SuperrDuperrException;
import com.instra.superrduperr.models.Cart;
import com.instra.superrduperr.models.Item;
import com.instra.superrduperr.models.Tag;
import com.instra.superrduperr.models.UserCarts;
import com.instra.superrduperr.repository.CartRepository;
import com.instra.superrduperr.repository.ItemRepository;

@Service
public class CartServiceImpl implements CartService {
	
	private static Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private ItemRepository itemsRepo;

	@Override
	public UserCarts getCartsByUserId(int userId){
		List<Carts> dbCarts = cartRepo.findByUserId(userId);
		logger.debug(String.format("Carts available for %d are %d", userId, dbCarts.size()));
		UserCarts userCart = new UserCarts(userId);
		List<Cart> carts = new ArrayList<>();
		dbCarts.forEach(dbCart->{
			carts.add(new Cart(dbCart.getId(),dbCart.getName()));
		});
		userCart.setCarts(carts);
		return userCart;
	}

	@Transactional
	@Override
	public int createCartForUser(int userId, String cartName, List<Item> items) throws SuperrDuperrException {
		Carts carts = new Carts();
		carts.setUserId(userId);
		carts.setName(cartName);
		cartRepo.save(carts);
		logger.debug(String.format("%s created with id %d", carts.getName(), carts.getId()));
		if(Objects.isNull(items) || items.isEmpty()) {
			throw new SuperrDuperrException("400 Bad Request", "There are no items to add");
		}
		if(Objects.nonNull(items)) {
			List<Items> dbItems = new ArrayList<>();
			items.forEach(item -> {
				Items dbItem = new Items();
				dbItem.setCarts(carts);
				dbItem.setProductId(item.getProductId());
				dbItem.setQuantity(item.getQuantity());
				dbItem.setVersion(1);
				dbItems.add(dbItem);
			});
			itemsRepo.saveAll(dbItems);
			logger.debug("All items saved");
		}
		return carts.getId();
	}

	@Override
	public void addItemsToCart(int cartId, List<Item> items) throws SuperrDuperrException {
		Carts carts = cartRepo.getOne(cartId);
		if(Objects.isNull(carts)) {
			throw new SuperrDuperrException("404 cartId not found", String.format("%d invalid cartId", cartId));
		}
		if(Objects.isNull(items) || items.isEmpty()) {
			throw new SuperrDuperrException("400 Bad Request", "There are no items to add");
		}
		int latestVersion = 0;
		if(Objects.isNull(itemsRepo.findByCartId(cartId))) {
			latestVersion = 1;
		} else {
			latestVersion = itemsRepo.findLatestVersionForCart(cartId)+1;
		}
		int version = latestVersion;
		if(Objects.nonNull(items)) {
			List<Items> dbItems = items.stream().map(item -> {
				Items dbItem = new Items();
				dbItem.setCarts(carts);
				dbItem.setProductId(item.getProductId());
				dbItem.setQuantity(item.getQuantity());
				dbItem.setVersion(version);
				return dbItem;
			}).collect(Collectors.toList());
			itemsRepo.saveAll(dbItems);
			logger.debug(String.format("All items saved for cart %d", cartId));
		}
		
	}

	@Override
	public void deleteItemsFromCart(int cartId, List<Integer> productIds) throws SuperrDuperrException {
		Carts carts = cartRepo.getOne(cartId);
		if(Objects.isNull(carts)) {
			throw new SuperrDuperrException("404 cartId not found", String.format("%d invalid cartId", cartId));
		}
		if(Objects.isNull(productIds) || productIds.isEmpty()) {
			throw new SuperrDuperrException("400 Bad Request", "There are no items to delete");
		}
		int latestVersion = itemsRepo.findLatestVersionForCart(cartId)+1;
		if(Objects.nonNull(productIds)) {
			List<Items> dbItems = itemsRepo.findByProductIds(productIds, cartId);
			if(Objects.isNull(dbItems) || dbItems.size()<productIds.size()) {
				throw new SuperrDuperrException("400 Bad Request", "Invalid productIds");
			}
			List<Items> newItemsList = dbItems.stream().map(item -> {
				Items newItem = new Items();
				newItem.setProductId(item.getProductId());
				newItem.setCarts(item.getCarts());
				newItem.setQuantity(-1);
				newItem.setVersion(latestVersion);
				return newItem;
			}).collect(Collectors.toList());
			itemsRepo.saveAll(newItemsList);
			logger.debug(String.format("All items updated for cart %d", cartId));
		}
		
	}

	@Transactional
	@Override
	public List<Item> restoreCart(int cartId) throws SuperrDuperrException {
		Carts carts = cartRepo.getOne(cartId);
		if(Objects.isNull(carts)) {
			throw new SuperrDuperrException("404 cartId not found", String.format("%d invalid cartId", cartId));
		}
		logger.debug("removed latest version");
		itemsRepo.deleteLastVersionForCartId(cartId);
		logger.debug("removed latest version");
		List<Items> dbItems = itemsRepo.findByCartId(cartId);
		logger.debug("available items "+ dbItems.size());
		List<Item> items = calculateActualItemList(dbItems);
		return items;
	}

	private List<Item> calculateActualItemList(List<Items> dbItems) {
		Map<Integer, Item> productIdToItem = new HashMap<>();
		dbItems.forEach(dbItem->{
			Item itemFromMap = productIdToItem.get(dbItem.getProductId());
			if(Objects.isNull(itemFromMap)) {
				Item item = new Item();
				item.setProductId(dbItem.getProductId());
				item.setQuantity(dbItem.getQuantity());
				item.setTag(dbItem.getTag());
				productIdToItem.put(item.getProductId(), item);
			} else {
				if((itemFromMap.getQuantity() + dbItem.getQuantity())>0) {
					itemFromMap.setQuantity(itemFromMap.getQuantity() + dbItem.getQuantity());
				} else {
					productIdToItem.remove(dbItem.getProductId());
				}
			}
		});
		return productIdToItem.values().stream().collect(Collectors.toList());
	}

	@Override
	public List<Item> getItemsForCart(int cartId) throws SuperrDuperrException {
		Carts carts = cartRepo.getOne(cartId);
		if(Objects.isNull(carts)) {
			throw new SuperrDuperrException("404 cartId not found", String.format("%d invalid cartId", cartId));
		}
		List<Items> dbItems = itemsRepo.findByCartId(cartId);
		List<Item> items = calculateActualItemList(dbItems);
		return items;
	}

	@Transactional
	@Override
	public void tagItemsInCart(Integer cartId, Tag tag) throws SuperrDuperrException {
		Carts carts = cartRepo.getOne(cartId);
		if(Objects.isNull(carts)) {
			throw new SuperrDuperrException("404 cartId not found", String.format("%d invalid cartId", cartId));
		}
		itemsRepo.updateTagForProductsByCartId(cartId, tag.getProductIds(), tag.getName());
	}

}
