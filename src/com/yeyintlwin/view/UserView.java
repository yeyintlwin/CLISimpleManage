package com.yeyintlwin.view;

import java.util.List;
import java.util.Scanner;

import com.yeyintlwin.dao.UserDAO;
import com.yeyintlwin.entity.User;
import com.yeyintlwin.util.ConsoleIOManager;
import com.yeyintlwin.view.ui.TableComponent;

public class UserView {

	private UserDAO userDAO;

	public UserView() {

		userDAO = new UserDAO();
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void create(Scanner scanner) {
		System.out.print("ユーザー名を入力してください: ");
		String name = ConsoleIOManager.coloredStringInput(scanner);

		System.out.print("ユーザー残高を入力してください: ");
		int balance = ConsoleIOManager.coloredIntInput(scanner);

		scanner.nextLine(); // console bug, Consume the newline character

		User user = new User();
		user.setName(name);
		user.setWalletAmount(balance);

		userDAO.create(user);

		ConsoleIOManager.printSuccess("ユーザーが正常に作成されました:");

		String[][] table = { { "Id", "Name", "Balance" },
				{ String.valueOf(user.getId()), user.getName(), user.getWalletAmount().toString() } };

		TableComponent.createTable().setTableData(table).print();
	}

	public void read() {
		System.out.println("ユーザーリスト:");
		try {
			List<User> users = userDAO.read();

			String[][] table = new String[users.size() + 1][3];
			table[0][0] = "Id";
			table[0][1] = "Name";
			table[0][2] = "Balance";

			for (int i = 1; i < users.size() + 1; i++) {
				table[i][0] = String.valueOf(users.get(i - 1).getId());
				table[i][1] = users.get(i - 1).getName();
				table[i][2] = String.valueOf(users.get(i - 1).getWalletAmount());
			}

			TableComponent.createTable().setTableData(table).print();

		} catch (Exception e) {
			ConsoleIOManager.printError(e.getMessage());

		}

	}

	public void read(int id) {
		try {
			User user = userDAO.read(id);

			String[][] table = { { "Id", "Name", "Balance" },
					{ String.valueOf(user.getId()), user.getName(), user.getWalletAmount().toString() } };

			TableComponent.createTable().setTableData(table).print();

		} catch (Exception e) {
			ConsoleIOManager.printError(e.getMessage());
		}
	}

	public void update(Scanner scanner, int id) {

		try {
			User dbUser = userDAO.read(id);

			User oldUser = new User();
			oldUser.setId(dbUser.getId());
			oldUser.setName(dbUser.getName());
			oldUser.setWalletAmount(dbUser.getWalletAmount());

			boolean isNameChanged = false;
			boolean isBalanceChanged = false;

			System.out.print("新しい名前を入力してください（変更しない場合は空白のままにしてください）:");
			String newName = ConsoleIOManager.coloredStringInput(scanner);
			if (!newName.isEmpty()) {
				dbUser.setName(newName);
				isNameChanged = true;
			}

			System.out.print("新しい残高を入力してください（変更しない場合は空白のままにしてください）:");
			String balanceStr = ConsoleIOManager.coloredStringInput(scanner);
			if (!balanceStr.isEmpty()) {
				int newBalance = Integer.parseInt(balanceStr);
				dbUser.setWalletAmount(newBalance);
				isBalanceChanged = true;
			}

			ConsoleIOManager.printSuccess("ユーザーが正常に更新されました:   ");

			String[][] table = { { "Id", "Name", "Balance" },
					{ String.valueOf(dbUser.getId()),
							isNameChanged ? oldUser.getName() + " -> " + dbUser.getName() : dbUser.getName(),
							isBalanceChanged ? oldUser.getWalletAmount() + " -> " + dbUser.getWalletAmount()
									: dbUser.getWalletAmount().toString() } };

			TableComponent.createTable().setTableData(table).print();

			// to update JSON file
			userDAO.update();

		} catch (Exception e) {
			ConsoleIOManager.printError(e.getMessage());
			return;
		}

	}

	public void delete(int id) {
		try {
			userDAO.delete(id);
			ConsoleIOManager.printWarning(id + "のユーザーが正常に削除されました:");
		} catch (Exception e) {
			ConsoleIOManager.printError(e.getMessage());
		}

	}

}
