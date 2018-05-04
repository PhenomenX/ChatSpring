package com.epam.chatspring.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.epam.chatspring.dao.UserDAO;
import com.epam.chatspring.model.User;

public class AuthenticationInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserDAO userDAO;

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
		HttpSession httpSession = request.getSession();
		User user = (User) httpSession.getAttribute("currentUser");
		logger.debug("Authentication user");
		if(user!=null){
			if(userDAO.isLogged(user)){
				logger.debug(" User autenticated");
				return true;
			} else {
				logger.debug("User not log in. Response with error was sended");
				response.setHeader("message", authenticationError);
				response.sendError(401, authenticationError);
				return false;
			}
		}
		logger.debug("Current user not exist. Response with error was sended");
		response.setHeader("message", authenticationError);
		response.sendError(401, authenticationError);
		return false;
	}

}
