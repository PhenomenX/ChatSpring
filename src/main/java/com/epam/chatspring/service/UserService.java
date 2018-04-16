package com.epam.chatspring.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.epam.chatspring.dao.datalayer.data.Role;
import com.epam.chatspring.dao.datalayer.data.Status;
import com.epam.chatspring.dao.datalayer.data.User;

public interface UserService {

	void register(User user);
	Role login(User user, HttpSession httpSession);
	void logout(User user, HttpSession httpSession);
	User getUser(User user);
	List<User> getUsers(Status status);
	
}
