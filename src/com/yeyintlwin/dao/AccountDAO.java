package com.yeyintlwin.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yeyintlwin.entity.Account;
import com.yeyintlwin.entity.User;
import com.yeyintlwin.util.FileIO;

public class AccountDAO {

	private FileIO jsonFileIO;
	private final String JSON_FILE_NAME = "accounts.json";
	private final String JSON_ARRAY_NAME = "accounts";

	private List<Account> accounts;

	private UserDAO userDAO;

	public AccountDAO(UserDAO userDAO) {

		this.userDAO = userDAO;

		accounts = new ArrayList<>();

		jsonFileIO = new FileIO();

		loadFromFile();
	}

	private void loadFromFile() {

		String jsonString;
		try {
			jsonString = jsonFileIO.readJSONFromFile(JSON_FILE_NAME);
		} catch (Exception e) {
			return;
		}
		if (jsonString.equals("{}")) {
			return;
		}
		JSONObject jsonObject = new JSONObject(jsonString);
		JSONArray jsonArray = jsonObject.getJSONArray(JSON_ARRAY_NAME);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject tempObject = (JSONObject) jsonArray.get(i);

			User theUser;
			try {
				theUser = userDAO.read(tempObject.getInt("owner_id"));
			} catch (JSONException e) {
				e.printStackTrace();
				return;
			} catch (Exception e) {
				e.printStackTrace();
				theUser = new User(-1, "Unknown", 0);
			}

			accounts.add(new Account(tempObject.getInt("id"), tempObject.getString("name"),
					tempObject.getInt("balance"), theUser));
		}

	}

	private void updateToFile() {

		JSONObject jsonObject = new JSONObject();

		JSONArray jsonArray = new JSONArray();

		for (Account tempAccount : accounts) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", tempAccount.getId());
			map.put("name", tempAccount.getName());
			map.put("balance", tempAccount.getBalance());
			map.put("owner_id", tempAccount.getTheOwner().getId());
			jsonArray.put(map);
		}

		jsonObject.put(JSON_ARRAY_NAME, jsonArray);
		try {
			jsonFileIO.writeJSON2File(JSON_FILE_NAME, jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Transistion
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
			if (tempAccount.getTheOwner().getId() == account.getTheOwner().getId()) {
				throw new Exception("複数のアカウントを作成することはできません。");
			}
		}

		accounts.add(account);
		updateToFile();
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

	// @Transistion
	// this method will not use.
	public void update() {
		updateToFile();
	}

	// @Transistion
	public void delete(int id) throws Exception {
		Account dbAccount = read(id);
		accounts.remove(dbAccount);
		updateToFile();
	}

}
