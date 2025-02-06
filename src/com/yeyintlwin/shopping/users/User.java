package com.yeyintlwin.shopping.users;

public class User {

	private int id;
	private String name;
	private Integer walletAmount;

	public User() {

	}

	public User(int theId, String theName, int theWalletAmount) {
		id = theId;
		name = theName;
		walletAmount = theWalletAmount;
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

	public Integer getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(int walletAmount) {
		this.walletAmount = walletAmount;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", walletAmount=" + walletAmount + "]";
	}

	
}
