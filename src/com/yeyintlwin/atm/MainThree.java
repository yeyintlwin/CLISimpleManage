package com.yeyintlwin.atm;

import java.util.ArrayList;
import java.util.List;

import com.yeyintlwin.atm.account.Account;
import com.yeyintlwin.atm.process.Process;

public class MainThree {

	public static void main(String[] args) {
		
		List<Account> accountsList = new ArrayList<Account>();
		
		accountsList.add(new Account(0,"John",100));
		accountsList.add(new Account(1,"Merry", 200));
		accountsList.add(new Account(3, "David", 300));
		accountsList.add(new Account(4,"Thomas", 150));
		
		Process process = new Process();
		process.display(accountsList);
		
	}

}
