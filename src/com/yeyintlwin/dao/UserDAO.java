package com.yeyintlwin.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.yeyintlwin.entity.User;
import com.yeyintlwin.util.FileIO;

public class UserDAO {
	private FileIO jsonFileIO;
	private final String JSON_FILE_NAME = "users.json";

	private List<User> users;

	public UserDAO() {

		users = new ArrayList<User>();

		jsonFileIO = new FileIO();
		
		loadFromFile();
	}

	private void loadFromFile() {
		try {
			String jsonString = jsonFileIO.readJSONFromFile(JSON_FILE_NAME);
			if (jsonString.equals("{}")) {
				return;
			}
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("users");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tempObject = (JSONObject) jsonArray.get(i);
				users.add(
						new User(tempObject.getInt("id"), tempObject.getString("name"), tempObject.getInt("balance")));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void updateToFile() {

		JSONObject jsonObject = new JSONObject();

		JSONArray jsonArray = new JSONArray();

		for (User tempUser : users) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", Integer.toString(tempUser.getId()));
			map.put("name", tempUser.getName());
			map.put("balance", Integer.toString(tempUser.getWalletAmount()));
			jsonArray.put(map);
		}

		jsonObject.put("users", jsonArray);
		try {
			jsonFileIO.writeJSON2File(JSON_FILE_NAME, jsonObject.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Transistion
	public void create(User user) {

		/* Primary Key Auto Increase */
		int largestUserId = 0;

		for (User tempUser : users) {
			int id = tempUser.getId();
			if (id > largestUserId) {
				largestUserId = id;
			}
		}

		user.setId(largestUserId + 1);

		users.add(user);

		updateToFile();

	}

	public List<User> read() throws Exception {

		if (users.isEmpty()) {
			throw new Exception("利用可能なユーザーはありません。");
		}

		return users;
	}

	public User read(int id) throws Exception {

		for (User tempUser : users) {
			if (tempUser.getId() == id) {
				return tempUser;
			}
		}

		throw new Exception("ID: " + id + " のユーザーが見つかりません。");
	}

	// @Transistion
	// This method does't used.
	public void update() {
		updateToFile();
	}

	// @Transistion
	public void delete(int id) throws Exception {
		User user = read(id);
		users.remove(user);
		updateToFile();

	}

}
