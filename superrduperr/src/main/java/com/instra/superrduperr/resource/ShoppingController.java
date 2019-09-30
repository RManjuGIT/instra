package com.instra.superrduperr.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instra.superrduperr.exception.SuperrDuperrException;
import com.instra.superrduperr.models.Item;
import com.instra.superrduperr.models.Tag;
import com.instra.superrduperr.models.UserCarts;
import com.instra.superrduperr.service.CartService;

@RestController
@RequestMapping(value = "/shopping/cart")
public class ShoppingController {

	@Autowired
	private CartService cartService;

	@GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserCarts getShoppingCartsForUserId(@PathVariable("userId") Integer userId) {
		return cartService.getCartsByUserId(userId);
	}

	@PostMapping(value = "/user/{userId}/{cartName}",
			consumes = MediaType.APPLICATION_JSON_VALUE) 
	public int createCartForUserId(@PathVariable("userId") Integer userId,
			@PathVariable("cartName") String cartName,
			@RequestBody List<Item> items) throws SuperrDuperrException {
		return cartService.createCartForUser(userId, cartName, items);
	}
	
	@PutMapping(value = "/additems/{cartId}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void	addItemsFromCartWithId(@PathVariable("cartId") Integer cartId,
			@RequestBody List<Item> items) throws SuperrDuperrException {
		cartService.addItemsToCart(cartId, items);; 
	}

	@PutMapping(value = "/deleteitems/{cartId}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteItemsFromCartWithId(@PathVariable("cartId") Integer cartId,
			@RequestBody List<Integer> productIds) throws SuperrDuperrException {
		cartService.deleteItemsFromCart(cartId, productIds);
	}
	
	@GetMapping(value = "/restore/{cartId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Item> restoreCartWithCartId(@PathVariable("cartId") Integer cartId) throws SuperrDuperrException{
		return cartService.restoreCart(cartId);
	}
	
	@GetMapping(value = "/{cartId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Item> getShoppingCartsForUserIdAndCartId(@PathVariable("cartId") Integer cartId) throws SuperrDuperrException{
		return cartService.getItemsForCart(cartId); 
	}
	
	@PutMapping(value = "/tagitems/{cartId}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void tagItemwithCartId(@PathVariable("cartId") Integer cartId,
			@RequestBody Tag tag) throws SuperrDuperrException{
		System.out.println("Request for restore::::::::::::");
		cartService.tagItemsInCart(cartId, tag);
	}

}
