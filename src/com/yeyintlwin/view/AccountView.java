package com.yeyintlwin.view;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.yeyintlwin.dao.AccountDAO;
import com.yeyintlwin.dao.UserDAO;
import com.yeyintlwin.entity.Account;
import com.yeyintlwin.entity.User;
import com.yeyintlwin.util.ConsoleIOManager;
import com.yeyintlwin.view.ui.TableComponent;

public class AccountView {

	private AccountDAO accountDAO;
	private UserDAO userDAO;

	public AccountView(UserDAO userDAO) {
		accountDAO = new AccountDAO(userDAO);
		this.userDAO = userDAO;
	}

	public void create(Scanner scanner) {
		System.out.print("アカウント名を入力してください: ");
		String name = ConsoleIOManager.coloredStringInput(scanner);

		System.out.print("残高を入力してください: ");
		int balance = ConsoleIOManager.coloredIntInput(scanner);

		System.out.print("所有者のユーザーIDを入力してください: ");
		int ownerId = ConsoleIOManager.coloredIntInput(scanner);

		scanner.nextLine(); // console bug, Consume the newline character

		Account account = new Account();
		account.setName(name);
		account.setBalance(balance);

		User user;
		try {
			user = userDAO.read(ownerId);
		} catch (Exception e) {
			ConsoleIOManager.printError(e.getMessage());
			return;
		}

		account.setTheOwner(user);

		try {
			accountDAO.create(account);
		} catch (Exception e) {
			ConsoleIOManager.printError(e.getMessage());
			return;
		}

		ConsoleIOManager.printSuccess("アカウントが正常に作成されました: ");

		String[][] table = { { "Id", "Name", "Balance", "OwnerId" },
				{ String.valueOf(account.getId()), account.getName(), String.valueOf(account.getBalance()),
						String.valueOf(account.getTheOwner().getId()) } };

		TableComponent.createTable().setTableData(table).print();

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
				table[i][3] = String.valueOf(accounts.get(i - 1).getTheOwner().getId());
			}

			TableComponent.createTable().setTableData(table).print();

		} catch (Exception e) {
			ConsoleIOManager.printError(e.getMessage());
		}

	}

	public void read(int id) {

		try {
			Account account = accountDAO.read(id);

			String[][] table = { { "Id", "Name", "Balance", "OwnerId" },
					{ String.valueOf(account.getId()), account.getName(), String.valueOf(account.getBalance()),
							String.valueOf(account.getTheOwner().getId()) } };

			TableComponent.createTable().setTableData(table).print();
		} catch (Exception e) {
			ConsoleIOManager.printError(e.getMessage());
		}

	}

	public void update(Scanner scanner, int id) {

		try {
			Account dbAccount = accountDAO.read(id);

			Account oldAccount = new Account();
			oldAccount.setId(dbAccount.getId());
			oldAccount.setName(dbAccount.getName());
			oldAccount.setBalance(dbAccount.getBalance());
			oldAccount.setTheOwner(dbAccount.getTheOwner());

			boolean isNameChanged = false;
			boolean isBalanceChanged = false;

			System.out.print("新しい名前を入力してください（変更しない場合は空白のままにしてください）: ");
			String newName = ConsoleIOManager.coloredStringInput(scanner);

			if (!newName.isEmpty()) {
				dbAccount.setName(newName);
				isNameChanged = true;
			}

			System.out.print("新しい価格を入力してください（変更しない場合は空白のままにしてください）: ");
			String newBalanceStr = ConsoleIOManager.coloredStringInput(scanner);
			if (!newBalanceStr.isEmpty()) {
				int newBalance = Integer.parseInt(newBalanceStr);
				dbAccount.setBalance(newBalance);
				isBalanceChanged = true;
			}

			ConsoleIOManager.printSuccess("アカウントが正常に更新されました: ");

			String[][] table = { { "Id", "Name", "Balance", "OwnerId" },
					{ String.valueOf(dbAccount.getId()),
							isNameChanged ? dbAccount.getName() + " -> " + dbAccount.getName() : dbAccount.getName(),
							String.valueOf(isBalanceChanged ? oldAccount.getBalance() + " -> " + dbAccount.getBalance()
									: dbAccount.getBalance()),
							String.valueOf(dbAccount.getTheOwner().getId()) } };

			TableComponent.createTable().setTableData(table).print();

			accountDAO.update();

		} catch (Exception e) {
			e.printStackTrace();
			// ConsoleIOManager.printError(e.getMessage());
		}

	}

	public void delete(int id) {
		try {
			accountDAO.delete(id);
			ConsoleIOManager.printWarning("ID: " + id + " のアカウントが正常に削除されました");

		} catch (Exception e) {
			ConsoleIOManager.printError(e.getMessage());
		}
	}

	public void deposit(Scanner scanner, int id) {
		Account dbAccount;
		try {
			dbAccount = accountDAO.read(id);
		} catch (Exception e) {
			ConsoleIOManager.printError("D預金に失敗しました！\n" + e.getMessage());
			return;
		}

		Account oldAccount = new Account();
		oldAccount.setBalance(dbAccount.getBalance());

		System.out.print("金額を入力してください: ");
		int amount = ConsoleIOManager.coloredIntInput(scanner);
		scanner.nextLine(); // console bug, Consume the newline character

		dbAccount.setBalance(dbAccount.getBalance() + amount);

		ConsoleIOManager.printSuccess("預金が正常に更新されました！: ");

		String[][] table = { { "Id", "Name", "Balance", "OwnerId" },
				{ String.valueOf(dbAccount.getId()), dbAccount.getName(),
						oldAccount.getBalance() + " -> " + dbAccount.getBalance(),
						String.valueOf(dbAccount.getTheOwner().getId()) } };

		TableComponent.createTable().setTableData(table).print();

		// update data file
		userDAO.update();
		accountDAO.update();

	}

	public void withdraw(Scanner scanner, int id) {
		Account dbAccount;
		User dbAccountOwner;

		try {
			dbAccount = accountDAO.read(id);
		} catch (Exception e) {
			ConsoleIOManager.printError(e.toString());
			return;
		}

		System.out.print("Enter amount: ");
		int amount = ConsoleIOManager.coloredIntInput(scanner);
		scanner.nextLine(); // console bug, Consume the newline character

		if (dbAccount.getBalance() < amount) {
			ConsoleIOManager.printSuccess("引き出しに失敗しました！\n残高が不足しています。");
			return;
		}

		try {
			dbAccountOwner = userDAO.read(dbAccount.getTheOwner().getId());
		} catch (Exception e) {
			ConsoleIOManager.printError("アカウント所有者が見つかりません。\nお金を引き出すことはできません。");
			return;
		}

		/* START:TEMP */
		int oldAccountBalance = dbAccount.getBalance();
		int oldAccountOwnerBalance = dbAccountOwner.getWalletAmount();
		/* END:TEMP */

		/* START:TRAN */
		dbAccount.setBalance(dbAccount.getBalance() - amount);
		dbAccountOwner.setWalletAmount(dbAccountOwner.getWalletAmount() + amount);
		/* END:TRAN */

		/* DISPLAY OUTPUT */

		ConsoleIOManager.printSuccess("引き出しが成功しました！");
		System.out.println("銀行アカウントにて: ");
		String[][] table = { { "Id", "Name", "Balance", "OwnerId" },
				{ String.valueOf(dbAccount.getId()), dbAccount.getName(),
						String.valueOf(oldAccountBalance + " -> " + dbAccount.getBalance()),
						String.valueOf(dbAccount.getTheOwner().getId()) } };

		TableComponent.createTable().setTableData(table).print();

		System.out.println("ユーザーアカウントにて: ");

		String[][] table2 = { { "Id", "Name", "Balance" }, { String.valueOf(dbAccountOwner.getId()),
				dbAccountOwner.getName(), oldAccountOwnerBalance + " -> " + dbAccountOwner.getWalletAmount() } };

		TableComponent.createTable().setTableData(table2).print();

		// update data file
		userDAO.update();
		accountDAO.update();

	}

	public void deleteRelatedUserAccount(int userId) {

		try {
			userDAO.read(userId);

		} catch (Exception e) {
			return;
		}

		List<Account> accounts;
		try {
			accounts = accountDAO.read();
		} catch (Exception e) {
			return;
		}

		Iterator<Account> iterator = accounts.iterator();
		while (iterator.hasNext()) {
			Account tempAccount = iterator.next();
			if (tempAccount.getTheOwner().getId() == userId) {

				ConsoleIOManager.printWarning("ユーザー関連のアカウントも削除されました。");
				iterator.remove(); // Safe removal
				accountDAO.update();
				return;
			}
		}

	}

}
