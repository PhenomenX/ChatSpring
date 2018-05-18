package com.epam.chatspring.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class UserMap {
	private Map<String, User> users = new HashMap<String, User>();

	public Map<String, User> getUsers() {
		return users;
	}

	public void setUsers(Map<String, User> users) {
		this.users = users;
	}

	public void addUser(User user) {
		String name = user.getName();
		users.put(name, user);
	}

	public boolean containsKey(String key) {
		return users.containsKey(key);
		
	}

	public String toString() {
		StringBuilder usersString = new StringBuilder();
		for (User user : users.values()) {
			System.out.println(user);
		}
		return usersString.toString();
	}

	public void remove(String name) {
		users.remove(name);

	}

}
