package com.epam.chatspring.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.chatspring.dao.datalayer.UserDAO;
import com.epam.chatspring.dao.datalayer.data.Role;
import com.epam.chatspring.dao.datalayer.data.User;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public void register(User user) {
		userDAO.createUser(user);

	}

	@Override
	public Role login(User user, HttpSession httpSession) {
		Role role = userDAO.getRole(user.getName());
		user.setRole(role);
		httpSession.setAttribute("currentUser", user);
		userDAO.logIn(user);
		return role;
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
