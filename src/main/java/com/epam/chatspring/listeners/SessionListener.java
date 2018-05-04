package com.epam.chatspring.listeners;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.epam.chatspring.dao.DAOFactory;
import com.epam.chatspring.dao.DBType;
import com.epam.chatspring.dao.UserDAO;
import com.epam.chatspring.model.User;

public class SessionListener implements HttpSessionListener {

	public SessionListener() {
	}

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		HttpSession httpSession = httpSessionEvent.getSession();
		httpSession.setMaxInactiveInterval(300);
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		UserDAO userDAO = factory.getUserDAO();
		HttpSession httpSession = httpSessionEvent.getSession();
		User user = (User) httpSession.getAttribute("currentUser");
		userDAO.logOut(user);
	}

}
