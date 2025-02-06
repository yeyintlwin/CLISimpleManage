package com.yeyintlwin.shopping.items;

public class Item {

	private int id;
	private String name;
	private int price;

	public Item() {
	}

	public Item(int theId, String theName, int thePrice) {
		id = theId;
		name = theName;
		price = thePrice;
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
		return "Item [id=" + id + ", name=" + name + ", price=" + price + "]";
	}

	
	
}
