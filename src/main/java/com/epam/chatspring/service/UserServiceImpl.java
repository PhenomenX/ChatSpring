package com.epam.chatspring.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epam.chatspring.dao.MessageDAO;
import com.epam.chatspring.dao.UserDAO;
import com.epam.chatspring.exceptions.NameValidationException;
import com.epam.chatspring.model.Message;
import com.epam.chatspring.model.Status;
import com.epam.chatspring.model.User;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private MessageDAO messageDAO;
	
	@Autowired
	private User currentUser;

	@Value("${message.login}")
	private String loginMessage;

	@Value("${message.logout}")
	private String logoutMessage;

	@Value("${message.error.login}")
	private String loginErrorMessage;

	@Value("${message.error.login.kick}")
	private String kickedUserMessage;
	
	@Value("${message.error.registration}")
	private String registrationErrorMessage;

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Override
	public void register(User user) throws NameValidationException {
		if(userDAO.isUnique(user.getName())){
		userDAO.createUser(user);
		} else {
			throw new NameValidationException(registrationErrorMessage);
		}
	}

	@Override
	public User login(User user) {
		User fullUser = userDAO.getUser(user.getName());
		user.setRole(fullUser.getRole());
		user.setStatus(fullUser.getStatus());
		user.setPicturePath(fullUser.getPicturePath());
		logger.debug(user.getPicturePath());
		userDAO.logIn(user);
		String messageText = String.format(loginMessage, user.getName());
		Message message = new Message(user.getName(), messageText);
		messageDAO.sendMessage(message);
		return fullUser;
	}

	@Override
	public void logout(User user) {
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
