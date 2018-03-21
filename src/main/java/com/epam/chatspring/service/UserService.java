package com.epam.chatspring.service;

import java.util.List;

import com.epam.chatspring.dao.datalayer.data.User;

public interface UserService {

	void register(User user);
	void login(User user);
	void logout(User user);
	User getUser(User user);
	List<User> getUsers(User user);
	
}
