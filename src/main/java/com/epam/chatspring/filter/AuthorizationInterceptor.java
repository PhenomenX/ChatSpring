package com.epam.chatspring.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.epam.chatspring.dao.DAOFactory;
import com.epam.chatspring.dao.DBType;
import com.epam.chatspring.dao.UserDAO;

public class AuthorizationInterceptor implements HandlerInterceptor {

	@Autowired
	UserDAO userDAO;

	@Value("${message.error.login}")
	private String loginErrorMessage;

	private static final Logger logger = Logger.getLogger(AuthorizationInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String nick = request.getParameter("nick");
		String password = request.getParameter("password");
		int id = userDAO.isValid(nick, password);
		if (id == 0) {
			response.sendError(401, loginErrorMessage);
			return false;
		} else {
			return true;
		}
	}
}
