package com.epam.chatspring.listeners;

import java.util.Enumeration;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.epam.chatspring.controller.LoginController;
import com.epam.chatspring.model.User;
import com.epam.chatspring.model.UserMap;

public class SessionListener implements HttpSessionListener {
	private static final Logger logger = Logger.getLogger(LoginController.class);

	public SessionListener() {
	}

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		logger.info("Session was created");
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		UserMap userMap = ctx.getBean(UserMap.class);
		User user = ctx.getBean(User.class);
		String sessionId = session.getId();
		MDC.put("sessionId", sessionId);
		Enumeration e = session.getAttributeNames();
		while (e.hasMoreElements()) {
		String name = (String)e.nextElement();
		String value = session.getAttribute(name).toString();
		System.out.println("name is: " + name + " value is: " + value);
		}
		//userMap.remove(user.getName());
		logger.info("Session was destroyed");
	}
}
