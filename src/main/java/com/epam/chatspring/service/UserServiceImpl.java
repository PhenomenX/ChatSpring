package com.epam.chatspring.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.epam.chatspring.dao.datalayer.DAOFactory;
import com.epam.chatspring.dao.datalayer.DBType;
import com.epam.chatspring.dao.datalayer.MessageDAO;
import com.epam.chatspring.dao.datalayer.UserDAO;
import com.epam.chatspring.dao.datalayer.data.Role;
import com.epam.chatspring.dao.datalayer.data.User;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	private DAOFactory factory;
	private UserDAO userDAO;

	public UserServiceImpl() {
		this.factory = DAOFactory.getInstance(DBType.ORACLE);
		this.userDAO = factory.getUserDAO();
	}

	@Override
	public void register(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void login(User user, HttpSession httpSession) {
		Role role = userDAO.getRole(user.getName());
		user.setRole(role);
		httpSession.setAttribute("currentUser", user);
		userDAO.logIn(user);
	}

	@Override
	public void logout(User user, HttpSession httpSession) {
		httpSession.removeAttribute("currentUser");
		userDAO.logOut(user);
	}

	@Override
	public User getUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsers(User user) {
		return userDAO.getAllLogged();
	}

}
