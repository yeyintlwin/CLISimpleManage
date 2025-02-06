package com.yeyintlwin.shopping.process;

import com.yeyintlwin.shopping.items.Item;
import com.yeyintlwin.shopping.users.User;

public class Buy {

	public Buy() {
	}

	public void userFromShop(User user, Item item) {
		user.setWalletAmount(user.getWalletAmount() - item.getPrice());
		System.out.println(user.getName() + "は、" + item.getName() + "(" + item.getPrice() + ")を買いました。");
		System.out.println(user.getName() + "さんの残高は" + user.getWalletAmount() + "円になりました。\n");
	}

	//	No　Sense
	public void userFromUser(User user, Item item) {
		user.setWalletAmount(user.getWalletAmount() - item.getPrice());
		System.out.println(user.getName() + "は、" + item.getName() + "(" + item.getPrice() + ")を買いました。");
		System.out.println(user.getName() + "さんの残高は" + user.getWalletAmount() + "円になりました。\n");

	}

}
