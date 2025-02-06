package com.yeyintlwin.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yeyintlwin.entity.Item;
import com.yeyintlwin.entity.User;
import com.yeyintlwin.util.FileIO;

public class ItemDAO {

	private FileIO jsonFileIO;
	private final String JSON_FILE_NAME = "items.json";
	private final String JSON_ARRAY_NAME = "items";

	List<Item> items;
	UserDAO userDAO;

	public ItemDAO(UserDAO userDAO) {

		this.userDAO = userDAO;

		items = new ArrayList<Item>();

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

			User theOwnerBean;
			try {
				theOwnerBean = userDAO.read(tempObject.getInt("owner_id"));
			} catch (JSONException e) {
				e.printStackTrace();
				return;
			} catch (Exception e) {
				theOwnerBean = new User(-1, "Unknown User", 0);
			}

			items.add(new Item(tempObject.getInt("id"), tempObject.getString("name"), tempObject.getInt("price"),
					theOwnerBean));
		}

	}

	private void updateToFile() {

		JSONObject jsonObject = new JSONObject();

		JSONArray jsonArray = new JSONArray();

		for (Item tempItem : items) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", tempItem.getId());
			map.put("name", tempItem.getName());
			map.put("price", tempItem.getPrice());
			map.put("owner_id", tempItem.getTheOwner().getId());
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
	public void create(Item item) {

		/* Primary Key Auto Increase */
		int largestItemId = 0;
		for (Item tempitem : items) {
			int id = tempitem.getId();
			if (id > largestItemId) {
				largestItemId = id;
			}
		}
		item.setId(largestItemId + 1);

		items.add(item);
		updateToFile();
	}

	public List<Item> read() throws Exception {
		if (items.isEmpty()) {
			throw new Exception("利用可能なアイテムはありません。");
		}
		return items;
	}

	public Item read(int id) throws Exception {
		for (Item tempItem : items) {
			if (tempItem.getId() == id) {
				return tempItem;
			}
		}
		throw new Exception("ID: " + id + " のアイテムが見つかりません");
	}

	// @Transistion
	// This method will not use
	public void update() {
		updateToFile();
	}

	public void delete(int id) throws Exception {
		Item item = read(id);
		items.remove(item);
		updateToFile();
	}
}
