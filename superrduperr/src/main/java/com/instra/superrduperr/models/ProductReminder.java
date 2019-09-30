package com.instra.superrduperr.models;

import java.util.List;

public class ProductReminder {

	private String name;
	private List<Integer> productIds;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getProductIds() {
		return productIds;
	}
	public void setProductIds(List<Integer> productIds) {
		this.productIds = productIds;
	}
	
}
