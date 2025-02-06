package com.yeyintlwin.entity;

public class Account {

	private int id;
	private String name;
	private int balance;
	private User theOwner;

	public Account() {

	}

	public Account(int id, String name, int balance) {
		this.id = id;
		this.name = name;
		this.balance = balance;
	}

	public Account(int id, String name, int balance, User theOwner) {
		this.id = id;
		this.name = name;
		this.balance = balance;
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

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public User getTheOwner() {
		return theOwner;
	}

	public void setTheOwner(User theOwner) {
		this.theOwner = theOwner;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", balance=" + balance + ", theOwner=" + theOwner + "]";
	}

}
