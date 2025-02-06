package com.yeyintlwin.shopping.items;

import com.yeyintlwin.shopping.users.User;

public class Item2 {
	private int id;
	private String name;
	private int price;
	private User user;

	public Item2() {
	}

	public Item2(int id, String name, int price, User user) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Item2 [id=" + id + ", name=" + name + ", price=" + price + ", user=" + user + "]";
	}

}
