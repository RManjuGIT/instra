package com.instra.superrduperr.models;

import java.util.List;

public class UserCarts {

	private int userId;
	private List<Cart> carts;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<Cart> getCarts() {
		return carts;
	}
	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}
	public UserCarts(int userId) {
		this.userId = userId;
	}
	
}
