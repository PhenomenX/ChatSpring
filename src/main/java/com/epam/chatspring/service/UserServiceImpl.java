package com.epam.chatspring.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epam.chatspring.dao.MessageDAO;
import com.epam.chatspring.dao.UserDAO;
import com.epam.chatspring.model.Message;
import com.epam.chatspring.model.Status;
import com.epam.chatspring.model.User;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private MessageDAO messageDAO;

	@Value( "${message.login}" )
	String loginMessage;
	
	@Value( "${message.logout}" )
	String logoutMessage;

	
	@Override
	public void register(User user) {
		userDAO.createUser(user);
	}

	@Override
	public User login(User user, HttpSession httpSession) {
		user = userDAO.getUser(user.getName());
		httpSession.setAttribute("currentUser", user);
		userDAO.logIn(user);
		user =  userDAO.getUser(user.getName());
		String messageText = String.format(loginMessage, user.getName());
		Message message = new Message(user.getName(), messageText);
		messageDAO.sendMessage(message);
		return user;
	}

	@Override
	public void logout(User user, HttpSession httpSession) {
		httpSession.removeAttribute("currentUser");
		userDAO.logOut(user);
		String messageText = String.format(logoutMessage, user.getName());
		Message message = new Message(user.getName(), messageText);
		messageDAO.sendMessage(message);
	}

	@Override
	public User getUser(String nick) {
		return userDAO.getUser(nick);
	}

	@Override
	public List<User> getUsers(Status status) {
		return userDAO.getUsersByStatus(status);
	}

}
