package com.yeyintlwin.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.yeyintlwin.dao.ItemDAO;
import com.yeyintlwin.dao.UserDAO;
import com.yeyintlwin.shopping.items.Item2;
import com.yeyintlwin.shopping.users.User;
import com.yeyintlwin.util.Utils;
import com.yeyintlwin.view.ui.CustomTable;

public class ItemView {

	ItemDAO itemDAO;
	UserDAO userDAO;

	public ItemView(UserDAO theUserDAO) {
		itemDAO = new ItemDAO();
		userDAO = theUserDAO;
	}

	public void create(Scanner scanner) {
		System.out.print("アイテム名を入力してください: ");
		String name = Utils.coloredStringInput(scanner);

		System.out.print("アイテム価格を入力してください: ");
		int price = Utils.coloredIntInput(scanner);

		System.out.print("所有者のユーザーIDを入力してください: ");
		int ownerId = Utils.coloredIntInput(scanner);

		scanner.nextLine(); // console bug, Consume the newline character

		User owner;

		try {
			owner = userDAO.read(ownerId);
		} catch (Exception e) {
			Utils.printError(e.getMessage());
			return;
		}

		Item2 item = new Item2();
		item.setName(name);
		item.setPrice(price);
		item.setUser(owner);

		itemDAO.create(item);

		Utils.printSuccess("アイテムが正常に作成されました: ");

		String[][] table = { { "Id", "Name", "Price", "Owner Id" }, { String.valueOf(item.getId()), item.getName(),
				String.valueOf(item.getPrice()), String.valueOf(item.getUser().getId()) } };

		CustomTable.createTable().setTableData(table).print();
	}

	public void read() {
		System.out.println("アイテムリスト:");
		try {
			List<Item2> items = itemDAO.read();
//			System.out.println(items);

			String[][] table = new String[items.size() + 1][4];
			table[0][0] = "Id";
			table[0][1] = "Name";
			table[0][2] = "Price";
			table[0][3] = "Owner Id";

			for (int i = 1; i < items.size() + 1; i++) {
				table[i][0] = String.valueOf(items.get(i - 1).getId());
				table[i][1] = items.get(i - 1).getName();
				table[i][2] = String.valueOf(items.get(i - 1).getPrice());
				table[i][3] = String.valueOf(items.get(i - 1).getUser().getId());
			}

			CustomTable.createTable().setTableData(table).print();

		} catch (Exception e) {
			Utils.printError(e.getMessage());
		}
	}

	public void read(int id) {
		try {
			Item2 item = itemDAO.read(id);
//			System.out.println(item);

			String[][] table = { { "Id", "Name", "Price", "Owner Id" }, { String.valueOf(item.getId()), item.getName(),
					String.valueOf(item.getPrice()), String.valueOf(item.getUser().getId()) } };

			CustomTable.createTable().setTableData(table).print();
		} catch (Exception e) {
			Utils.printError(e.getMessage());
		}
	}

	public void update(Scanner scanner, int id) {

		try {
			Item2 dbItem = itemDAO.read(id);

			Item2 oldItem = new Item2();
			oldItem.setId(dbItem.getId());
			oldItem.setName(dbItem.getName());
			oldItem.setPrice(dbItem.getPrice());
			oldItem.setUser(dbItem.getUser());

			boolean isNameChanged = false;
			boolean isPriceChanged = false;

			System.out.print("新しい名前を入力してください（変更しない場合は空白のままにしてください）:");
			String newName = Utils.coloredStringInput(scanner);

			if (!newName.isEmpty()) {
				dbItem.setName(newName);
				isNameChanged = true;
			}

			System.out.print("新しい価格を入力してください（変更しない場合は空白のままにしてください）: ");
			String newPriceStr = Utils.coloredStringInput(scanner);
			if (!newPriceStr.isEmpty()) {
				int newPrice = Integer.parseInt(newPriceStr);
				dbItem.setPrice(newPrice);
				isPriceChanged = true;
			}

			Utils.printSuccess("アイテムが正常に更新されました:");

			String[][] table = { { "Id", "Name", "Price", "Owner Id" }, { String.valueOf(dbItem.getId()),
					isNameChanged ? oldItem.getName() + " -> " + dbItem.getName() : dbItem.getName(),
					String.valueOf(
							isPriceChanged ? oldItem.getPrice() + " -> " + dbItem.getPrice() : dbItem.getPrice()),
					String.valueOf(dbItem.getUser().getId()) } };

			CustomTable.createTable().setTableData(table).print();

		} catch (Exception e) {
			Utils.printError(e.getMessage());
		}
	}

	public void delete(int id) {
		try {
			itemDAO.delete(id);
			Utils.printWarning("ID: " + id + " のアイテムが正常に削除されました");

		} catch (Exception e) {
			Utils.printError(e.getMessage());
		}

	}

	public void showUserOwnedItems(int userId) {

		User user;
		try {
			user = userDAO.read(userId);

		} catch (Exception e) {
			Utils.printError(e.getMessage());
			return;
		}

		System.out.println("ユーザー " + user.getName() + " (ID: " + userId + ") が所有するアイテム:");

		List<Item2> ownerItems = new ArrayList<>();
		List<Item2> items;

		try {
			items = itemDAO.read();

		} catch (Exception e) {
			Utils.printError("このユーザーにはアイテムが見つかりませんでした。");
			return;
		}

		for (Item2 tempItem : items) {
			if (tempItem.getUser().getId() == userId) {
				ownerItems.add(tempItem);
			}
		}

		if (ownerItems.isEmpty()) {
			Utils.printError("このユーザーにはアイテムが見つかりませんでした。");
			return;
		}

//		System.out.println(ownerItems);

		String[][] table = new String[ownerItems.size() + 1][3];
		table[0][0] = "Id";
		table[0][1] = "Name";
		table[0][2] = "Price";

		for (int i = 1; i < ownerItems.size() + 1; i++) {
			table[i][0] = String.valueOf(ownerItems.get(i - 1).getId());
			table[i][1] = ownerItems.get(i - 1).getName();
			table[i][2] = String.valueOf(ownerItems.get(i - 1).getPrice());
		}

		CustomTable.createTable().setTableData(table).print();

	}

	public void sellItem(int itemId, int buyerId) {

		Item2 item;
		User buyer;
		User seller;

		try {
			item = itemDAO.read(itemId);
		} catch (Exception e) {
			Utils.printError("ID: " + itemId + " のアイテムが見つかりません。");
			return;
		}

		try {
			buyer = userDAO.read(buyerId);
		} catch (Exception e) {
			Utils.printError("ID: " + buyerId + " の購入者が見つかりません。");
			return;
		}

		seller = item.getUser();
		if (seller == null) {
			Utils.printError("アイテムに所有者がいません。販売できません。");
			return;
		}

		if (buyer.getWalletAmount() < item.getPrice()) {
			Utils.printError("取引に失敗しました！\n購入者の残高が不足しています。");
			return;
		}

		// Update balances and transfer ownership
		buyer.setWalletAmount(buyer.getWalletAmount() - item.getPrice());
		seller.setWalletAmount(seller.getWalletAmount() + item.getPrice());
		item.setUser(buyer);

		Utils.printSuccess("取引が正常に完了しました！");
		System.out.println("販売者: " + seller.getName() + "、新しい残高: " + seller.getWalletAmount());
		System.out.println("購入者: " + buyer.getName() + "、新しい残高: " + buyer.getWalletAmount());
		System.out.println("アイテム  '" + item.getName() + "'  は " + buyer.getName() + " に販売されました");

	}

}
