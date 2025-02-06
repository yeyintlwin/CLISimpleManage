package com.yeyintlwin.atm.process;

import java.util.List;

import com.yeyintlwin.atm.account.Account;

public class Process {

	public void display(List<Account> accounts) {

		for (int i = 0; i < accounts.size(); i++) {

			Account tempAccount = accounts.get(i);

			System.out.println(tempAccount.getName() + "の口座番号は" + tempAccount.getId());
		}
	}

	public void displayUsingWhileLoop(List<Account> accounts) {
		int i = 0;
		while (true) {

			Account tempAccount = accounts.get(i);

			System.out.println(tempAccount.getName() + "の口座番号は" + tempAccount.getId());

			i++;
			if (i == accounts.size())
				break;
		}
	}

	public void displayUsingForAsWhileLoop(List<Account> accounts) {
		int i = 0;
		for (;;) {

			Account tempAccount = accounts.get(i);

			System.out.println(tempAccount.getName() + "の口座番号は" + tempAccount.getId());

			i++;
			if (i == accounts.size())
				break;
		}
	}

	public void displayUsingDoWhileLoop(List<Account> accounts) {
		int i = 0;
		do {
			Account tempAccount = accounts.get(i);
			System.out.println(tempAccount.getName() + "の口座番号は" + tempAccount.getId());
			i++;
		} while (accounts.size() != i);
	}

	public void displayUsingForEach(List<Account> accounts) {

		for (Account tempAccount : accounts) {
			System.out.println(tempAccount.getName() + "の口座番号は" + tempAccount.getId());

		}
	}

	public void display(Account[] accounts) {

		for (int i = 0; i < accounts.length; i++) {

			System.out.println(accounts[i].getName() + "の口座番号は" + accounts[i].getId());
		}

	}

}
