package com.yeyintlwin.atm.account;

import com.yeyintlwin.shopping.users.User;

public class Account {

	private int id;
	private String name;
	private int balance;
	private User owner;

	public Account() {

	}

	public Account(int id, String name, int balance) {
		this.id = id;
		this.name = name;
		this.balance = balance;
	}

	public Account(int id, String name, int balance, User owner) {
		this.id = id;
		this.name = name;
		this.balance = balance;
		this.owner = owner;
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

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", balance=" + balance + ", owner=" + owner + "]";
	}

}
