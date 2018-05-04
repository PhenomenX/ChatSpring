package com.epam.chatspring.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.epam.chatspring.model.Role;
import com.epam.chatspring.model.User;

public class AdminInterceptor implements HandlerInterceptor {
	
	@Value("${message.error.authentication.admin}")
	private String grantError;
	@Value("${message.error.authentication}")
	private String authenticationError;
	
	private static final Logger logger = Logger.getLogger(AuthenticationInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception arg3)
			throws Exception {	
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView arg3)
			throws Exception {	
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		HttpSession httpSession = request.getSession();
		User user = (User) httpSession.getAttribute("currentUser");
		logger.debug("Checking user at admin role");
		if(user!=null){
			Role role = user.getRole();
			if(role.equals(Role.ADMIN)){
				logger.debug("User is admin");
				return true;
			} else {
				logger.debug("User not admin. Send response with error");
				response.setHeader("message", grantError);
				response.sendError(403, grantError);
				return false;
			}
		}
		logger.debug("Current user not exist. Response with error was sended");
		response.setHeader("message", authenticationError);
		response.sendError(401, authenticationError);
		return false;
	}

}
