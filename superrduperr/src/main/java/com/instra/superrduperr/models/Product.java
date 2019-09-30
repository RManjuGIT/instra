package com.instra.superrduperr.models;

public class Product {

	private int id;
	private String name;
	private Double price;
	private Boolean inStock;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean isInStock() {
		return inStock;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Product(int id, String name, boolean inStock, Double price) {
		this.id = id;
		this.name = name;
		this.inStock = inStock;
		this.price = price;
	}
	
}
