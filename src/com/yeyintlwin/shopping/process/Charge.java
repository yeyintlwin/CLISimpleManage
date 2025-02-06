package com.yeyintlwin.shopping.process;

import com.yeyintlwin.atm.account.Account;
import com.yeyintlwin.credit.user.CreditUser;
import com.yeyintlwin.shopping.users.User;

public class Charge {

	public void fromBank(Account account, User user, int amount) {

		if (!account.getName().equals(user.getName())) {
			System.out.println("チャージできませんでした。\n銀行口座名とユーザー名が異なります。\n");
			return;
		}

		if (account.getBalance() < amount) {
			System.out.println("チャージできませんでした。\n");
			return;
		}
		user.setWalletAmount(user.getWalletAmount() + amount);
		account.setBalance(account.getBalance() - amount);

		System.out.println(user.getName() + "さんのアカウントに" + amount + "円チャージさらました。");
		System.out.println(user.getName() + "さんの残高は" + user.getWalletAmount() + "円になりました。\n");

	}

	public void formCreditCard(CreditUser creditUser, User user, int amount) {
		if (creditUser.getLimit() < amount) {
			System.out.println("チャージできませんでした。\nクレジットカードの限度額に達しました。\n");
			return;
		}

		user.setWalletAmount(user.getWalletAmount() + amount);
		creditUser.setLimit(creditUser.getLimit() - amount);

		System.out.println(user.getName() + "さんのアカウントに" + amount + "円チャージさらました。(by credit card)");
		System.out.println(user.getName() + "さんの残高は" + user.getWalletAmount() + "円になりました。\n");

	}

}
