package com.yeyintlwin.dao;

import java.util.ArrayList;
import java.util.List;

import com.yeyintlwin.shopping.items.Item2;

public class ItemDAO {

	List<Item2> items;

	public ItemDAO() {

		items = new ArrayList<Item2>();
	}

	public void create(Item2 item) {

		/* Primary Key Auto Increase */
		int largestItemId = 0;
		for (Item2 tempitem : items) {
			int id = tempitem.getId();
			if (id > largestItemId) {
				largestItemId = id;
			}
		}
		item.setId(largestItemId + 1);

		items.add(item);
	}

	public List<Item2> read() throws Exception {
		if (items.isEmpty()) {
			throw new Exception("利用可能なアイテムはありません。");
		}
		return items;
	}

	public Item2 read(int id) throws Exception {
		for (Item2 tempItem : items) {
			if (tempItem.getId() == id) {
				return tempItem;
			}
		}
		throw new Exception("ID: " + id + " のアイテムが見つかりません");
	}

	// This method will not use
	public void update(int id) throws Exception {
	}

	public void delete(int id) throws Exception {
		Item2 item = read(id);
		items.remove(item);
	}
}
