package com.yeyintlwin.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.yeyintlwin.dao.ItemDAO;
import com.yeyintlwin.dao.UserDAO;
import com.yeyintlwin.entity.Item;
import com.yeyintlwin.entity.User;
import com.yeyintlwin.util.ConsoleIOManager;
import com.yeyintlwin.view.ui.TableComponent;

public class ItemView {

	ItemDAO itemDAO;
	UserDAO userDAO;

	public ItemView(UserDAO theUserDAO) {

		userDAO = theUserDAO;
		itemDAO = new ItemDAO(userDAO);
	}

	public void create(Scanner scanner) {
		System.out.print("アイテム名を入力してください: ");
		String name = ConsoleIOManager.coloredStringInput(scanner);

		System.out.print("アイテム価格を入力してください: ");
		int price = ConsoleIOManager.coloredIntInput(scanner);

		System.out.print("所有者のユーザーIDを入力してください: ");
		int ownerId = ConsoleIOManager.coloredIntInput(scanner);

		scanner.nextLine(); // console bug, Consume the newline character

		User owner;

		try {
			owner = userDAO.read(ownerId);
		} catch (Exception e) {
			ConsoleIOManager.printError(e.getMessage());
			return;
		}

		Item item = new Item();
		item.setName(name);
		item.setPrice(price);
		item.setTheOwner(owner);

		itemDAO.create(item);

		ConsoleIOManager.printSuccess("アイテムが正常に作成されました: ");

		String[][] table = { { "Id", "Name", "Price", "Owner Id" }, { String.valueOf(item.getId()), item.getName(),
				String.valueOf(item.getPrice()), String.valueOf(item.getTheOwner().getId()) } };

		TableComponent.createTable().setTableData(table).print();
	}

	public void read() {
		System.out.println("アイテムリスト:");
		try {
			List<Item> items = itemDAO.read();

			String[][] table = new String[items.size() + 1][4];
			table[0][0] = "Id";
			table[0][1] = "Name";
			table[0][2] = "Price";
			table[0][3] = "Owner Id";

			for (int i = 1; i < items.size() + 1; i++) {
				table[i][0] = String.valueOf(items.get(i - 1).getId());
				table[i][1] = items.get(i - 1).getName();
				table[i][2] = String.valueOf(items.get(i - 1).getPrice());
				table[i][3] = String.valueOf(items.get(i - 1).getTheOwner().getId());
			}

			TableComponent.createTable().setTableData(table).print();

		} catch (Exception e) {
			ConsoleIOManager.printError(e.getMessage());
		}
	}

	public void read(int id) {
		try {
			Item item = itemDAO.read(id);

			String[][] table = { { "Id", "Name", "Price", "Owner Id" }, { String.valueOf(item.getId()), item.getName(),
					String.valueOf(item.getPrice()), String.valueOf(item.getTheOwner().getId()) } };

			TableComponent.createTable().setTableData(table).print();
		} catch (Exception e) {
			ConsoleIOManager.printError(e.getMessage());
		}
	}

	public void update(Scanner scanner, int id) {

		try {
			Item dbItem = itemDAO.read(id);

			Item oldItem = new Item();
			oldItem.setId(dbItem.getId());
			oldItem.setName(dbItem.getName());
			oldItem.setPrice(dbItem.getPrice());
			oldItem.setTheOwner(dbItem.getTheOwner());

			boolean isNameChanged = false;
			boolean isPriceChanged = false;

			System.out.print("新しい名前を入力してください（変更しない場合は空白のままにしてください）:");
			String newName = ConsoleIOManager.coloredStringInput(scanner);

			if (!newName.isEmpty()) {
				dbItem.setName(newName);
				isNameChanged = true;
			}

			System.out.print("新しい価格を入力してください（変更しない場合は空白のままにしてください）: ");
			String newPriceStr = ConsoleIOManager.coloredStringInput(scanner);
			if (!newPriceStr.isEmpty()) {
				int newPrice = Integer.parseInt(newPriceStr);
				dbItem.setPrice(newPrice);
				isPriceChanged = true;
			}

			ConsoleIOManager.printSuccess("アイテムが正常に更新されました:");

			String[][] table = { { "Id", "Name", "Price", "Owner Id" }, { String.valueOf(dbItem.getId()),
					isNameChanged ? oldItem.getName() + " -> " + dbItem.getName() : dbItem.getName(),
					String.valueOf(
							isPriceChanged ? oldItem.getPrice() + " -> " + dbItem.getPrice() : dbItem.getPrice()),
					String.valueOf(dbItem.getTheOwner().getId()) } };

			TableComponent.createTable().setTableData(table).print();

			// to update JSON file
			itemDAO.update();

		} catch (Exception e) {
			ConsoleIOManager.printError(e.getMessage());
		}
	}

	public void delete(int id) {
		try {
			itemDAO.delete(id);
			ConsoleIOManager.printWarning("ID: " + id + " のアイテムが正常に削除されました");

		} catch (Exception e) {
			ConsoleIOManager.printError(e.getMessage());
		}

	}

	public void showUserOwnedItems(int userId) {

		User user;
		try {
			user = userDAO.read(userId);

		} catch (Exception e) {
			ConsoleIOManager.printError(e.getMessage());
			return;
		}

		System.out.println("ユーザー " + user.getName() + " (ID: " + userId + ") が所有するアイテム:");

		List<Item> ownerItems = new ArrayList<>();
		List<Item> items;

		try {
			items = itemDAO.read();

		} catch (Exception e) {
			ConsoleIOManager.printError("このユーザーにはアイテムが見つかりませんでした。");
			return;
		}

		for (Item tempItem : items) {
			if (tempItem.getTheOwner().getId() == userId) {
				ownerItems.add(tempItem);
			}
		}

		if (ownerItems.isEmpty()) {
			ConsoleIOManager.printError("このユーザーにはアイテムが見つかりませんでした。");
			return;
		}

		String[][] table = new String[ownerItems.size() + 1][3];
		table[0][0] = "Id";
		table[0][1] = "Name";
		table[0][2] = "Price";

		for (int i = 1; i < ownerItems.size() + 1; i++) {
			table[i][0] = String.valueOf(ownerItems.get(i - 1).getId());
			table[i][1] = ownerItems.get(i - 1).getName();
			table[i][2] = String.valueOf(ownerItems.get(i - 1).getPrice());
		}

		TableComponent.createTable().setTableData(table).print();

	}

	public void sellItem(int itemId, int buyerId) {

		Item item;
		User buyer;
		User seller;

		try {
			item = itemDAO.read(itemId);
		} catch (Exception e) {
			ConsoleIOManager.printError("ID: " + itemId + " のアイテムが見つかりません。");
			return;
		}

		try {
			buyer = userDAO.read(buyerId);
		} catch (Exception e) {
			ConsoleIOManager.printError("ID: " + buyerId + " の購入者が見つかりません。");
			return;
		}

		try {
			seller = userDAO.read(item.getTheOwner().getId());
		} catch (Exception e) {
			ConsoleIOManager.printError("アイテムに所有者がいません。販売できません。");
			return;
		}

		if (buyer.getWalletAmount() < item.getPrice()) {
			ConsoleIOManager.printError("取引に失敗しました！\n購入者の残高が不足しています。");
			return;
		}

		if (seller.getId() == buyer.getId()) {
			ConsoleIOManager.printError("取引に失敗しました！\n自分で商品を販売することはできません。");
			return;
		}

		// Update balances and transfer ownership
		buyer.setWalletAmount(buyer.getWalletAmount() - item.getPrice());
		seller.setWalletAmount(seller.getWalletAmount() + item.getPrice());
		item.getTheOwner().setId(buyer.getId());

		ConsoleIOManager.printSuccess("取引が正常に完了しました！");
		System.out.println("販売者: " + seller.getName() + "、新しい残高: " + seller.getWalletAmount());
		System.out.println("購入者: " + buyer.getName() + "、新しい残高: " + buyer.getWalletAmount());
		System.out.println("アイテム  '" + item.getName() + "'  は " + buyer.getName() + " に販売されました");

		// to update data file.
		userDAO.update();
		itemDAO.update();

	}

	public void deleteRelatedUserItem(int userId) {

		User user;
		try {
			user = userDAO.read(userId);

		} catch (Exception e) {
			return;
		}

		List<Item> ownerItems = new ArrayList<>();
		List<Item> items;

		try {
			items = itemDAO.read();

		} catch (Exception e) {
			return;
		}

		for (Item tempItem : items) {
			if (tempItem.getTheOwner().getId() == userId) {
				ownerItems.add(tempItem);
			}
		}

		if (ownerItems.isEmpty()) {
			return;
		}

		ConsoleIOManager.printWarning("ユーザー " + user.getName() + " (ID: " + userId + ") が所有するアイテムも削除されました:");

		String[][] table = new String[ownerItems.size() + 1][3];
		table[0][0] = "Id";
		table[0][1] = "Name";
		table[0][2] = "Price";

		for (int i = 1; i < ownerItems.size() + 1; i++) {
			table[i][0] = String.valueOf(ownerItems.get(i - 1).getId());
			table[i][1] = ownerItems.get(i - 1).getName();
			table[i][2] = String.valueOf(ownerItems.get(i - 1).getPrice());
		}

		TableComponent.createTable().setTableData(table).print();

		Iterator<Item> iterator = items.iterator();
		while (iterator.hasNext()) {
			Item tempItem = iterator.next();
			if (tempItem.getTheOwner().getId() == userId) {
				iterator.remove(); // Safe removal
			}
		}

		itemDAO.update();
	}

}
