package com.epam.chatspring.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.epam.chatspring.dao.datalayer.data.User;

public interface UserService {

	void register(User user);
	void login(User user, HttpSession httpSession);
	void logout(User user, HttpSession httpSession);
	User getUser(User user);
	List<User> getUsers(User user);
	
}
