package com.epam.chatspring.listeners;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.epam.chatspring.dao.DAOFactory;
import com.epam.chatspring.dao.DBType;
import com.epam.chatspring.dao.UserDAO;
import com.epam.chatspring.model.User;

public class SessionListener implements HttpSessionListener {

	public SessionListener() {
	}

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		ApplicationContext ctx = WebApplicationContextUtils
                .getWebApplicationContext(session.getServletContext());
        UserDAO userDAO = (UserDAO) ctx.getBean("userDAO");
        User user = (User) ctx.getBean("currentUser");
		userDAO.logOut(user);
	}

}
