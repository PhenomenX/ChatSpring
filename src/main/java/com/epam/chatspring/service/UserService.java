package com.epam.chatspring.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.epam.chatspring.dao.datalayer.data.Status;
import com.epam.chatspring.dao.datalayer.data.User;

public interface UserService {

	void register(User user);
	User login(User user, HttpSession httpSession);
	void logout(User user, HttpSession httpSession);
	User getUser(String nick);
	List<User> getUsers(Status status);
	
}
