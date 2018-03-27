package com.epam.chatspring.dao.datalayer;

import java.util.List;

import com.epam.chatspring.dao.datalayer.data.Role;
import com.epam.chatspring.dao.datalayer.data.User;

public interface UserDAO {
	void logIn(User user);

	void logOut(User user);

	boolean isLogged(User user);

	void kick(User admin, User kickableUser);

	void unkick(User user);

	boolean isKicked(User user);

	List<User> getAllLogged();

	Role getRole(String nick);
	
	List<User> getAllKicked();
	
	int isValid(String login, String password);
}
