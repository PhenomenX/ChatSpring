package com.epam.chatspring.service;

import java.util.List;

import com.epam.chatspring.exceptions.NameValidationException;
import com.epam.chatspring.model.Status;
import com.epam.chatspring.model.User;

public interface UserService {

	void register(User user) throws NameValidationException;
	User login(User user);
	void logout(User user);
	User getUser(String nick);
	List<User> getUsers(Status status);
	
}
