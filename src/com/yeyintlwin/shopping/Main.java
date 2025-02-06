package com.yeyintlwin.shopping;

import java.util.ArrayList;
import java.util.List;

import com.yeyintlwin.shopping.items.Item;
import com.yeyintlwin.shopping.process.Buy;
import com.yeyintlwin.shopping.process.Sell;
import com.yeyintlwin.shopping.process.Trade;
import com.yeyintlwin.shopping.users.User;

public class Main {

	public static void main(String[] args) {
		
		List<User> theUserList = new ArrayList<User>();
		theUserList.add(new User(1001, "John Doe", 5000));
		theUserList.add(new User(1002, "Merry", 10000));

		
		for(User tempUser: theUserList ) {
			System.out.println(tempUser.toString());	
		}
		
		System.out.println();
		
		List<Item> theItemsList = new ArrayList<Item>();
		theItemsList.add(new Item(1001, "Bag", 3000));
		theItemsList.add(new Item(1002, "Jacket", 5000));
		theItemsList.add(new Item(1003, "Shoes", 8000));
		
	
		Trade trade = new Trade();
		trade.tradeBetweenUser(theUserList.get(0), theUserList.get(1), theItemsList.get(0));

		Buy buy = new Buy();
		buy.userFromShop(theUserList.get(0), theItemsList.get(1));
		
		Sell sell = new Sell();
		sell.userToShop(theUserList.get(0), theItemsList.get(2));

	}

}
