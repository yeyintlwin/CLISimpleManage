package com.yeyintlwin.shopping.process;

import com.yeyintlwin.shopping.items.Item;
import com.yeyintlwin.shopping.items.Item2;
import com.yeyintlwin.shopping.users.User;

public class Sell {

	public void userToShop(User user, Item item) {
		user.setWalletAmount(user.getWalletAmount() + item.getPrice());
		System.out.println(user.getName() + "さんは、" + item.getName() + "(" + item.getPrice() + ")を売りました。");
		System.out.println(user.getName() + "さんの残高は" + user.getWalletAmount() + "円になりました。\n");

	}

	//	No　Sense
	public void userToUser(User user, Item item) {
		user.setWalletAmount(user.getWalletAmount() + item.getPrice());
		System.out.println(user.getName() + "さんは、" + item.getName() + "(" + item.getPrice() + ")を売りました。");
		System.out.println(user.getName() + "さんの残高は" + user.getWalletAmount() + "円になりました。\n");
	}

	public void sellTo(User customer, Item2 item) {
		
		User seller = item.getUser();
		
		seller.setWalletAmount(seller.getWalletAmount() + item.getPrice());
		customer.setWalletAmount(customer.getWalletAmount() - item.getPrice());

		System.out.println(item.getUser().getName() + "さんは、" + customer.getName() + "さんに" + item.getName() + "("
				+ item.getPrice() + ")を売りました。");
		
		System.out.println(item.getUser().getName() + "さんの残高は" + item.getUser().getWalletAmount() + "円になりました。");
		System.out.println(customer.getName() + "さんの残高は" + customer.getWalletAmount() + "円になりました。\n");

		// change the owner
		item.setUser(customer);

	}

}
