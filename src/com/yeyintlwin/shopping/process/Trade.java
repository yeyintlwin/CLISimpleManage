package com.yeyintlwin.shopping.process;

import com.yeyintlwin.shopping.items.Item;
import com.yeyintlwin.shopping.users.User;

public class Trade {

	public void tradeBetweenUser(User userA, User userB, Item item) {
		userA.setWalletAmount(userA.getWalletAmount() - item.getPrice());
		userB.setWalletAmount(userB.getWalletAmount() + item.getPrice());
		
		System.out.println(userA.getName() + "さんは、" + item.getName() + "(" + item.getPrice() + ")を買いました。");
		System.out.println(userA.getName()+"さんの残高は"+userA.getWalletAmount()+"円になりました。\n");
		
		System.out.println(userB.getName() + "さんは、" + item.getName() + "(" + item.getPrice() + ")を売りました。");
		System.out.println(userB.getName()+"さんの残高は"+userB.getWalletAmount()+"円になりました。\n");
		
	}

}
