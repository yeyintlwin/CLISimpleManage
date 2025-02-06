package com.yeyintlwin;

import java.util.Scanner;

import com.yeyintlwin.dao.UserDAO;
import com.yeyintlwin.util.ConsoleIOManager;
import com.yeyintlwin.view.AccountView;
import com.yeyintlwin.view.ItemView;
import com.yeyintlwin.view.UserView;

public class Main {

	private static UserView userView;
	private static UserDAO userDAO;
	private static ItemView itemView;
	private static AccountView accountView;

	public static void main(String[] args) {

		displayBanner();

		userView = new UserView();
		userDAO = userView.getUserDAO();
		itemView = new ItemView(userDAO);
		accountView = new AccountView(userDAO);

		Scanner scanner = new Scanner(System.in);

		String input;

		while (true) {
			System.out.print("コマンドを入力してください（'help' でコマンド一覧を表示、'exit' で終了）\nhome> ");
			input = ConsoleIOManager.coloredStringInput(scanner); // .trim(); to ignore the space
			switch (input) { // .toLowerCase(); to accept capital letters
			case "use shopping":
				handleShoppingCommands(scanner);
				break;
			case "use bank":
				handleBankCommands(scanner);
				break;
			case "use user":
				handleUserCommands(scanner);
				break;
			case "help":
				displayHomeHelp();
				break;
			case "exit":
				System.out.println("アプリケーションを終了します...");
				scanner.close();
				System.exit(0);
				break;
			default:
				ConsoleIOManager.printError("不明なコマンドです。利用可能なコマンドを確認するには 'help' と入力してください。");
			}
		}
	}

	private static void handleUserCommands(Scanner scanner) {

		String input;

		while (true) {

			System.out.print("コマンドを入力してください（コマンドリストは「help」、'home'に戻るには「exit」と入力）。\nuser> ");

			input = ConsoleIOManager.coloredStringInput(scanner);

			if (input.equalsIgnoreCase("exit")) {
				System.out.println("ユーザーを終了しています...");
				break;
			} else if (input.equalsIgnoreCase("help")) {
				displayUserHelp();
			} else if (input.equalsIgnoreCase("create user")) {
				userView.create(scanner);
			} else if (input.equalsIgnoreCase("read users")) {
				userView.read();
			} else if (input.startsWith("read user ")) {
				int id = Integer.parseInt(input.substring(9).trim());
				userView.read(id);
			} else if (input.startsWith("update user ")) {
				int id = Integer.parseInt(input.substring(11).trim());
				userView.update(scanner, id);
			} else if (input.startsWith("delete user ")) {
				int id = Integer.parseInt(input.substring(11).trim());
				// when you delete an user, it will also delete user related data.
				itemView.deleteRelatedUserItem(id);
				accountView.deleteRelatedUserAccount(id);
				userView.delete(id);
			} else if (input.startsWith("read items user ")) {
				int userId = Integer.parseInt(input.substring(15).trim());
				itemView.showUserOwnedItems(userId);
			} else {
				ConsoleIOManager.printError("不明なコマンドです。利用可能なコマンドを確認するには 'help' と入力してください。");
			}

		}

	}

	private static void handleShoppingCommands(Scanner scanner) {

		String input;

		while (true) {

			System.out.print("コマンドを入力してください（コマンドリストは「help」、'home'に戻るには「exit」と入力）。\nshopping> ");

			input = ConsoleIOManager.coloredStringInput(scanner);

			if (input.equalsIgnoreCase("exit")) {
				System.out.println("ショッピングを終了しています...");
				break;
			} else if (input.equalsIgnoreCase("help")) {
				displayShoppingHelp();
			} else if (input.equalsIgnoreCase("create item")) {
				itemView.create(scanner);
			} else if (input.equalsIgnoreCase("read items")) {
				itemView.read();
			} else if (input.startsWith("read items user ")) {
				// if you place this statement under "read item <id>" it
				// will occur an error.
				int userId = Integer.parseInt(input.substring(15).trim());
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
					ConsoleIOManager.printError("無効なコマンド形式です。次の形式を使用してください: sell item <item_id> to <buyer_id>");
				}
			} else {
				ConsoleIOManager.printError("不明なコマンドです。利用可能なコマンドを確認するには 'help' と入力してください。");
			}

		}
	}

	private static void handleBankCommands(Scanner scanner) {
		String input;
		while (true) {
			System.out.print("コマンドを入力してください（コマンドリストは「help」、'home'に戻るには「exit」と入力）。\nbank> ");
			input = ConsoleIOManager.coloredStringInput(scanner);
			if (input.equalsIgnoreCase("exit")) {
				System.out.println("銀行を終了しています...");
				break;
			} else if (input.equalsIgnoreCase("help")) {
				displayBankHelp();
			} else if (input.equalsIgnoreCase("create account")) {
				accountView.create(scanner);
			} else if (input.equalsIgnoreCase("read accounts")) {
				accountView.read();
			} else if (input.startsWith("read account ")) {
				int id = Integer.parseInt(input.substring(12).trim());
				accountView.read(id);
			} else if (input.startsWith("update account ")) {
				int id = Integer.parseInt(input.substring(14).trim());
				accountView.update(scanner, id);
			} else if (input.startsWith("delete account ")) {
				int id = Integer.parseInt(input.substring(14).trim());
				accountView.delete(id);
			} else if (input.startsWith("deposit account ")) {
				int id = Integer.parseInt(input.substring(15).trim());
				accountView.deposit(scanner, id);
			} else if (input.startsWith("withdraw account ")) {
				int id = Integer.parseInt(input.substring(16).trim());
				accountView.withdraw(scanner, id);
			} else {
				ConsoleIOManager.printError("不明なコマンドです。利用可能なコマンドを確認するには 'help' と入力してください。");
			}
		}
	}

	private static void displayBanner() {
		System.out.println();
		System.out.println("===============================================");
		System.out.println("               アプリへようこそ！               ");
		System.out.println("   ユーザー、ショッピング（アイテム）、銀行を管理   ");
		System.out.println("===============================================");
		System.out.println();
	}

	private static void displayHomeHelp() {
		System.out.println("ホームコマンド:");
		System.out.println("  use user                  - userモジュールに移動します。");
		System.out.println("  use shopping              - shoppingモジュールに移動します。");
		System.out.println("  use bank                  - bankモジュールに移動します。");
		System.out.println("  help                      - このヘルプメッセージを表示します。");
		System.out.println("  exit                      - アプリケーションを終了します。");
	}

	private static void displayUserHelp() {
		System.out.println("ユーザーコマンド:");
		System.out.println("  create user               - 新しいユーザーを作成します。");
		System.out.println("  read users                - すべてのユーザーをリストします。");
		System.out.println("  read user <id>            - 指定されたIDのユーザーの詳細を表示します。");
		System.out.println("  update user <id>          - 既存ユーザーを更新します。");
		System.out.println("  delete user <id>          - 指定されたIDのユーザーを削除します。");
		System.out.println("  read items user <userId>  - 特定ユーザーのすべてのアイテムをリストします。");
		displayGeneralHelp();
	}

	private static void displayShoppingHelp() {
		System.out.println("ショッピングコマンド:");
		System.out.println("  create item               - 新しいアイテムを作成します。");
		System.out.println("  read items                - すべてのアイテムをリストします。");
		System.out.println("  read item <id>            - 指定されたIDのアイテムの詳細を表示します。");
		System.out.println("  update item <id>          - 既存アイテムを更新します。");
		System.out.println("  delete item <id>          - 指定されたIDのアイテムを削除します。");
		System.out.println("  read items user <userId>  - 特定のユーザーが所有するアイテムをリストします。");
		System.out.println("  sell item <item_id> to <buyer_id> - アイテムを別のユーザーに販売します。");
		displayGeneralHelp();
	}

	private static void displayBankHelp() {
		System.out.println("銀行コマンド:");
		System.out.println("  create account            - 新しいアカウントを作成します。");
		System.out.println("  read accounts             - すべてのアカウントをリストします。");
		System.out.println("  read account <id>         - 指定されたIDのアカウントの詳細を表示します。");
		System.out.println("  update account <id>       - アカウントを更新します。");
		System.out.println("  delete account <id>       - 指定されたIDのアカウントを削除します。");
		System.out.println("  deposit account <id>      - 銀行口座にお金を入金します。");
		System.out.println("  withdraw account <id>     - 銀行口座からお金を引き出します。");

		displayGeneralHelp();
	}

	private static void displayGeneralHelp() {
		System.out.println("一般コマンド:");
		System.out.println("  help                      - このヘルプメッセージを表示します。");
		System.out.println("  exit                      - 現在のモジュールを終了し、homeに戻ります。");
	}

}
