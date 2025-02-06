package com.yeyintlwin.dao;

import java.util.ArrayList;
import java.util.List;

import com.yeyintlwin.atm.account.Account;

public class AccountDAO {

	private List<Account> accounts;

	public AccountDAO() {

		accounts = new ArrayList<>();
	}

	public void create(Account account) throws Exception {

		/* Primary Key Auto Increase */
		int largestAccountId = 0;
		for (Account tempAccount : accounts) {
			int id = tempAccount.getId();
			if (id > largestAccountId) {
				largestAccountId = id;
			}
		}
		account.setId(largestAccountId + 1);

		// not to create multiple accounts. one person should have one account.
		for (Account tempAccount : accounts) {
			if (tempAccount.getOwner().getId() == account.getOwner().getId()) {
				throw new Exception("複数のアカウントを作成することはできません。");
			}
		}

		accounts.add(account);
	}

	public List<Account> read() throws Exception {

		if (accounts.isEmpty()) {
			throw new Exception("利用可能なアカウントはありません。");
		}

		return accounts;
	}

	public Account read(int id) throws Exception {

		for (Account tempAccount : accounts) {
			if (tempAccount.getId() == id) {
				return tempAccount;
			}
		}
		throw new Exception("ID: " + id + " のアカウントが見つかりません。");
	}

	// this method will not use.
	public void update(Account account) {
	}

	public void delete(int id) throws Exception {
		Account dbAccount = read(id);
		accounts.remove(dbAccount);
	}

}
