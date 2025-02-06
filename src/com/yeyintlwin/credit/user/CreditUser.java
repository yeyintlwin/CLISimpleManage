package com.yeyintlwin.credit.user;

public class CreditUser {
	private int id;
	private String name;
	private int limit;

	public CreditUser() {
	}

	public CreditUser(int id, String name, int limit) {
		super();
		this.id = id;
		this.name = name;
		this.limit = limit;
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

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "CreditUser [id=" + id + ", name=" + name + ", limit=" + limit + "]";
	}

}
