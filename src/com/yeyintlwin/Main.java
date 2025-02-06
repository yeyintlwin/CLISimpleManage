package com.yeyintlwin;

import java.util.Scanner;

import com.yeyintlwin.dao.UserDAO;
import com.yeyintlwin.view.AccountView;
import com.yeyintlwin.view.ItemView;
import com.yeyintlwin.view.UserView;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		UserView userView = new UserView();

		UserDAO userDAO = userView.getUserDAO();

		ItemView itemView = new ItemView(userDAO);


		String input;

		while (true) {

			System.out.print("Enter a command (Type 'help' for command list, 'exit' to quit)\n> ");

			input = scanner.nextLine();
			if (input.equalsIgnoreCase("exit")) {
				System.out.println("Existing program...");
				break;
			} else if (input.equalsIgnoreCase("help")) {
				displayHelpJP();
			} else if (input.equalsIgnoreCase("create user")) {
				userView.create(scanner);
			} else if (input.equalsIgnoreCase("read user")) {
				userView.read();
			} else if (input.startsWith("read user ")) {
				int id = Integer.parseInt(input.substring(9).trim());
				userView.read(id);
			} else if (input.startsWith("update user ")) {
				int id = Integer.parseInt(input.substring(11).trim());
				userView.update(scanner, id);
			} else if (input.startsWith("delete user ")) {
				int id = Integer.parseInt(input.substring(11).trim());
				userView.delete(id);
			} else if (input.equalsIgnoreCase("create item")) {
				itemView.create(scanner);
			} else if (input.equalsIgnoreCase("read item")) {
				itemView.read();
			} else if (input.startsWith("read item user ")) {
				// if you place this statement under "read item <id>" it
				// will occur an error.
				int userId = Integer.parseInt(input.substring(14).trim());
				itemView.showUserOwnedItems(userId);
			} else if (input.startsWith("read item ")) {
				int id = Integer.parseInt(input.substring(9).trim());
				itemView.read(id);
			} else if (input.startsWith("update item ")) {
				int id = Integer.parseInt(input.substring(11).trim());
				itemView.update(scanner, id);
			} else if (input.startsWith("delete item ")) {
				int id = Integer.parseInt(input.substring(11).trim());
				itemView.delete(id);
			} else if (input.startsWith("sell item ")) {
				try {
					String[] parts = input.split(" ");
					int itemId = Integer.parseInt(parts[2]);
					int buyerId = Integer.parseInt(parts[4]);

					itemView.sellItem(itemId, buyerId);

				} catch (Exception e) {
					System.err.println("Invalid command format. Use: sell item <item_id> to <buyer_id>");
				}
			} else {
				System.err.println("Unknown command. Type 'help' for a list of commands.");
			}

		}

		scanner.close();

	}

	private static void displayHelpJP() {
		System.out.println("使用可能なコマンド一覧:");
		System.out.println("ユーザーコマンド:");
		System.out.println("  create user               - 新しいユーザーを作成します。ユーザーの名前と残高を入力するように求められます。");
		System.out.println("  read user                 - すべてのユーザーのリストを表示します。");
		System.out.println("  read user <id>            - 特定のユーザー（ID指定）の詳細を表示します。");
		System.out.println("  update user <id>          - 既存のユーザーを更新します。新しい名前と残高を入力するように求められます。");
		System.out.println("  delete user <id>          - 指定されたIDのユーザーを削除します。");
		System.out.println("  read item user <userId>   - 特定のユーザーが所有するすべてのアイテムを一覧表示します。");

		System.out.println("\nアイテムコマンド:");
		System.out.println("  create item               - 新しいアイテムを作成します。アイテムの名前、価格、所有者（ユーザーID）を入力するように求められます。");
		System.out.println("  read item                 - すべてのアイテムのリストを表示します。");
		System.out.println("  read item <id>            - 特定のアイテム（ID指定）の詳細を表示します。");
		System.out.println("  update item <id>          - 既存のアイテムを更新します。新しい名前、価格、所有者を入力するように求められます。");
		System.out.println("  delete item <id>          - 指定されたIDのアイテムを削除します。");

		System.out.println("\n取引コマンド:");
		System.out.println(
				"  sell item <item_id> to <buyer_id> - アイテムを別のユーザーに販売します。購入者が十分な残高を持っている場合に限り、所有権を移転し、残高を調整します。");

		System.out.println("\n一般コマンド:");
		System.out.println("  help                      - このヘルプメッセージを表示します。");
		System.out.println("  exit                      - プログラムを終了します。");
	}

}
