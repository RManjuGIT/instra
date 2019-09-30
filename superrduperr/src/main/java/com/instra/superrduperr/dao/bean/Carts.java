package com.instra.superrduperr.dao.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "carts")
public class Carts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@NotNull
	private String name;
	@NotNull
	private int userId;
//	@OneToMany(mappedBy = "items", cascade = CascadeType.ALL)
//	@JoinColumn(name="cart_id")
//	private Items items;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/*
	 * public Items getItem() { return items; } public void setItem(Items item) {
	 * this.items = item; }
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
