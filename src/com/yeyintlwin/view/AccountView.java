package com.yeyintlwin.view;

import java.util.List;
import java.util.Scanner;

import com.yeyintlwin.atm.account.Account;
import com.yeyintlwin.dao.AccountDAO;
import com.yeyintlwin.dao.UserDAO;
import com.yeyintlwin.shopping.users.User;
import com.yeyintlwin.util.Utils;
import com.yeyintlwin.view.ui.CustomTable;

public class AccountView {

	private AccountDAO accountDAO;
	private UserDAO userDAO;

	public AccountView(UserDAO userDAO) {
		accountDAO = new AccountDAO();
		this.userDAO = userDAO;
	}

	public void create(Scanner scanner) {
		System.out.print("アカウント名を入力してください: ");
		String name = Utils.coloredStringInput(scanner);

		System.out.print("残高を入力してください: ");
		int balance = Utils.coloredIntInput(scanner);

		System.out.print("所有者のユーザーIDを入力してください: ");
		int ownerId = Utils.coloredIntInput(scanner);

		scanner.nextLine(); // console bug, Consume the newline character

		Account account = new Account();
		account.setName(name);
		account.setBalance(balance);

		User user;
		try {
			user = userDAO.read(ownerId);
		} catch (Exception e) {
			Utils.printError(e.getMessage());
			return;
		}

		account.setOwner(user);

		try {
			accountDAO.create(account);
		} catch (Exception e) {
			Utils.printError(e.getMessage());
			return;
		}

		Utils.printSuccess("アカウントが正常に作成されました: ");

		String[][] table = { { "Id", "Name", "Balance", "OwnerId" }, { String.valueOf(account.getId()),
				account.getName(), String.valueOf(account.getBalance()), String.valueOf(account.getOwner().getId()) } };

		CustomTable.createTable().setTableData(table).print();

	}

	public void read() {
		System.out.println("アカウントリスト:");
		try {
			List<Account> accounts = accountDAO.read();

			String[][] table = new String[accounts.size() + 1][4];
			table[0][0] = "Id";
			table[0][1] = "Name";
			table[0][2] = "Balance";
			table[0][3] = "Owner Id";

			for (int i = 1; i < accounts.size() + 1; i++) {
				table[i][0] = String.valueOf(accounts.get(i - 1).getId());
				table[i][1] = accounts.get(i - 1).getName();
				table[i][2] = String.valueOf(accounts.get(i - 1).getBalance());
				table[i][3] = String.valueOf(accounts.get(i - 1).getOwner().getId());
			}

			CustomTable.createTable().setTableData(table).print();

		} catch (Exception e) {
			Utils.printError(e.getMessage());
		}

	}

	public void read(int id) {

		try {
			Account account = accountDAO.read(id);

			String[][] table = { { "Id", "Name", "Balance", "OwnerId" },
					{ String.valueOf(account.getId()), account.getName(), String.valueOf(account.getBalance()),
							String.valueOf(account.getOwner().getId()) } };

			CustomTable.createTable().setTableData(table).print();
		} catch (Exception e) {
			Utils.printError(e.getMessage());
		}

	}

	public void update(Scanner scanner, int id) {

		try {
			Account dbAccount = accountDAO.read(id);

			Account oldAccount = new Account();
			oldAccount.setId(dbAccount.getId());
			oldAccount.setName(dbAccount.getName());
			oldAccount.setBalance(dbAccount.getBalance());
			oldAccount.setOwner(dbAccount.getOwner());

			boolean isNameChanged = false;
			boolean isBalanceChanged = false;

			System.out.print("新しい名前を入力してください（変更しない場合は空白のままにしてください）: ");
			String newName = Utils.coloredStringInput(scanner);

			if (!newName.isEmpty()) {
				dbAccount.setName(newName);
				isNameChanged = true;
			}

			System.out.print("新しい価格を入力してください（変更しない場合は空白のままにしてください）: ");
			String newBalanceStr = Utils.coloredStringInput(scanner);
			if (!newBalanceStr.isEmpty()) {
				int newBalance = Integer.parseInt(newBalanceStr);
				dbAccount.setBalance(newBalance);
				isBalanceChanged = true;
			}

			Utils.printSuccess("アカウントが正常に更新されました: ");

			String[][] table = { { "Id", "Name", "Balance", "OwnerId" },
					{ String.valueOf(dbAccount.getId()),
							isNameChanged ? dbAccount.getName() + " -> " + dbAccount.getName() : dbAccount.getName(),
							String.valueOf(isBalanceChanged ? oldAccount.getBalance() + " -> " + dbAccount.getBalance()
									: dbAccount.getBalance()),
							String.valueOf(dbAccount.getOwner().getId()) } };

			CustomTable.createTable().setTableData(table).print();

		} catch (Exception e) {
			Utils.printError(e.getMessage());
		}

	}

	public void delete(int id) {
		try {
			accountDAO.delete(id);
			Utils.printWarning("ID: " + id + " のアカウントが正常に削除されました");

		} catch (Exception e) {
			Utils.printError(e.getMessage());
		}
	}

	public void deposit(Scanner scanner, int id) {
		Account dbAccount;
		try {
			dbAccount = accountDAO.read(id);
		} catch (Exception e) {
			Utils.printError("D預金に失敗しました！\n" + e.getMessage());
			return;
		}

		Account oldAccount = new Account();
		oldAccount.setBalance(dbAccount.getBalance());

		System.out.print("金額を入力してください: ");
		int amount = Utils.coloredIntInput(scanner);
		scanner.nextLine(); // console bug, Consume the newline character

		dbAccount.setBalance(dbAccount.getBalance() + amount);

		Utils.printSuccess("預金が正常に更新されました！: ");

		String[][] table = { { "Id", "Name", "Balance", "OwnerId" },
				{ String.valueOf(dbAccount.getId()), dbAccount.getName(),
						oldAccount.getBalance() + " -> " + dbAccount.getBalance(),
						String.valueOf(dbAccount.getOwner().getId()) } };

		CustomTable.createTable().setTableData(table).print();

	}

	public void withdraw(Scanner scanner, int id) {
		Account dbAccount;

		try {
			dbAccount = accountDAO.read(id);
		} catch (Exception e) {
			Utils.printError(e.toString());
			return;
		}

		System.out.print("Enter amount: ");
		int amount = Utils.coloredIntInput(scanner);
		scanner.nextLine(); // console bug, Consume the newline character

		if (dbAccount.getBalance() < amount) {
			Utils.printSuccess("引き出しに失敗しました！\n残高が不足しています。");
			return;
		}

		/* START:TEMP */
		Account oldAccount = new Account();
		oldAccount.setOwner(new User());
		User oldUser = oldAccount.getOwner();
		oldAccount.setBalance(dbAccount.getBalance());
		oldUser.setWalletAmount(dbAccount.getOwner().getWalletAmount());
		/* END:TEMP */

		/* START:TRAN */
		dbAccount.setBalance(dbAccount.getBalance() - amount);
		User dbOwner = dbAccount.getOwner();
		dbOwner.setWalletAmount(dbOwner.getWalletAmount() + amount);
		/* END:TRAN */

		/* DISPLAY OUTPUT */
		Utils.printSuccess("引き出しが成功しました！");
		System.out.println("銀行アカウントにて: ");
		String[][] table = { { "Id", "Name", "Balance", "OwnerId" },
				{ String.valueOf(dbAccount.getId()), dbAccount.getName(),
						String.valueOf(oldAccount.getBalance() + " -> " + dbAccount.getBalance()),
						String.valueOf(dbAccount.getOwner().getId()) } };

		CustomTable.createTable().setTableData(table).print();

		System.out.println("ユーザーアカウントにて: ");

		String[][] table2 = { { "Id", "Name", "Balance" }, { String.valueOf(dbOwner.getId()), dbOwner.getName(),
				oldUser.getWalletAmount() + " -> " + dbOwner.getWalletAmount() } };

		CustomTable.createTable().setTableData(table2).print();

	}

}
