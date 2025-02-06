package com.yeyintlwin.dao;

import java.util.ArrayList;
import java.util.List;

import com.yeyintlwin.shopping.users.User;

public class UserDAO {

	List<User> users;

	public UserDAO() {

		users = new ArrayList<User>();

	}

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

	// This method does't used.
	public void update(User user) throws Exception {

	}

	public void delete(int id) throws Exception {
		User user = read(id);
		users.remove(user);

	}

}
