package com.yeyintlwin.atm;

import com.yeyintlwin.atm.account.Account;
import com.yeyintlwin.atm.process.Process;

public class MainTwo {

	public static void main(String[] args) {
		
		Account[] accounts = new Account[3];

		accounts[0] = new Account();
		accounts[1] = new Account();
		accounts[2] = new Account();
		accounts[3] = new Account();

		
		Process process = new Process();
		process.display(accounts);
	}
	


}
