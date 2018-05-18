package com.epam.chatspring.dao;

import java.util.List;

import com.epam.chatspring.model.Role;
import com.epam.chatspring.model.Status;
import com.epam.chatspring.model.User;

public interface UserDAO {

	void kick(String nick);

	void unkick(String nick);

	boolean isKicked(User user);

	Role getRole(String nick);
	
	List<User> getAllKicked();
	
	boolean isValid(User user);
	
	void createUser(User user);

	List<User> getUsersByStatus(Status status);

	User getUser(String nick);
	
	boolean isUnique(String nick);
}
