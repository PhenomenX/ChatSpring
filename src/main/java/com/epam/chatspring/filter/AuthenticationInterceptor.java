package com.epam.chatspring.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.epam.chatspring.dao.UserDAO;
import com.epam.chatspring.model.User;

public class AuthenticationInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserDAO userDAO;

	private String authenticationError;
	
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
		if(user!=null){
			if(userDAO.isLogged(user)){
				return true;
			} else {
				response.sendError(401, authenticationError);
				return false;
			}
		}
		response.sendError(401, authenticationError);
		return false;
	}

}
