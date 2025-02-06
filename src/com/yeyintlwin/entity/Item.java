package com.yeyintlwin.entity;

public class Item {
	private int id;
	private String name;
	private int price;
	private User theOwner;

	public Item() {
	}

	public Item(int id, String name, int price, User theOwner) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.theOwner = theOwner;
	}

	public User getTheOwner() {
		return theOwner;
	}

	public void setTheOwner(User theOwner) {
		this.theOwner = theOwner;
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

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", price=" + price + ", theOwner=" + theOwner + "]";
	}

}
