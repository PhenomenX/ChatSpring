package com.epam.chatspring.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.epam.chatspring.dao.UserDAO;
import com.epam.chatspring.model.User;
import com.epam.chatspring.model.UserMap;

public class AuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private User currentUser;

	@Autowired
	private UserMap onlineUsers;
	
	@Value("${message.error.authentication}")
	private String authenticationError;

	private static final Logger logger = Logger.getLogger(AuthenticationInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		logger.debug("Authentication user");
		String userName = currentUser.getName();
		if (onlineUsers.containsKey(userName) && !userDAO.isKicked(currentUser)) {
			logger.debug(" User autenticated");
			return true;
		} else {
			logger.debug("User not log in. Response with error was sended");
			response.setHeader("message", authenticationError);
			response.sendError(HttpStatus.UNAUTHORIZED.value(), authenticationError);
			return false;
		}
	}

}
