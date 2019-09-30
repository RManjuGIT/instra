package com.instra.superrduperr.service;

import java.util.List;

import com.instra.superrduperr.exception.SuperrDuperrException;
import com.instra.superrduperr.models.Item;
import com.instra.superrduperr.models.Tag;
import com.instra.superrduperr.models.UserCarts;

public interface CartService {

	UserCarts getCartsByUserId(int userId);
	int createCartForUser(int userId, String cartName, List<Item> items) throws SuperrDuperrException;
	void addItemsToCart(int cartId, List<Item> items) throws SuperrDuperrException;
	void deleteItemsFromCart(int cartId, List<Integer> productIds) throws SuperrDuperrException;
	List<Item> restoreCart(int cartId) throws SuperrDuperrException;
	List<Item> getItemsForCart(int cartId) throws SuperrDuperrException;
	void tagItemsInCart(Integer cartId, Tag tag) throws SuperrDuperrException;
}
