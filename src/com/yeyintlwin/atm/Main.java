package com.yeyintlwin.atm;

import java.util.ArrayList;
import java.util.List;

import com.yeyintlwin.atm.account.Account;
import com.yeyintlwin.credit.user.CreditUser;
import com.yeyintlwin.shopping.items.Item;
import com.yeyintlwin.shopping.items.Item2;
import com.yeyintlwin.shopping.process.Charge;
import com.yeyintlwin.shopping.process.Sell;
import com.yeyintlwin.shopping.process.Trade;
import com.yeyintlwin.shopping.users.User;

public class Main {

	// ATM
	public static void main(String[] args) {

		
		List<User> theUsersList = new ArrayList<User>();
		
		theUsersList.add(new User(1001, "John Doe", 5000));
		theUsersList.add(new User(1002, "Merry", 10000));
		
		for(User tempUser: theUsersList) {
			System.out.println(tempUser.toString());
		}
		
		System.out.println();
		
		
		List<Item> theItemsList = new ArrayList<Item>();
		theItemsList.add(new Item(1001, "Bag", 3000));
		theItemsList.add(new Item(1002, "Jacket", 5000));
		theItemsList.add(new Item(1003, "Shoes", 8000));
				
		Trade trade = new Trade();
		trade.tradeBetweenUser(theUsersList.get(0), theUsersList.get(1), theItemsList.get(0));

		List<Account> theAccountsList= new ArrayList<Account>();
		theAccountsList.add(new Account(12345, "John Z Doe", 6000));

		Charge charge = new Charge();
		charge.fromBank(theAccountsList.get(0), theUsersList.get(0), 10000);
		charge.fromBank(theAccountsList.get(0), theUsersList.get(0), 5000);

		List<CreditUser> theCreditUsersList = new ArrayList<CreditUser>();
		theCreditUsersList.add(new CreditUser(1001, "John Doe", 100000));
		charge.formCreditCard(theCreditUsersList.get(0), theUsersList.get(1), 10000000);

		User ye = new User(1004, "イェ", 10000);
		User paing = new User(1005, "パイ", 10000);

		System.out.println(ye.toString());
		System.out.println(paing.toString() + "\n");

		Item2 waterBottle = new Item2(9999, "天然水", 100, ye);

		Sell sell = new Sell();

		sell.sellTo(paing, waterBottle);

	}

}
