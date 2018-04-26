package com.epam.chatspring.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.epam.chatspring.dao.datalayer.DAOFactory;
import com.epam.chatspring.dao.datalayer.DBType;
import com.epam.chatspring.dao.datalayer.UserDAO;

public class AuthorizationInterceptor implements HandlerInterceptor {
	
	DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
	UserDAO userDAO = factory.getUserDAO();

	private static final Logger logger = Logger.getLogger(AuthorizationInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String nick = request.getParameter("nick");
		String password = request.getParameter("password");
		logger.info("LOGGER IS WORK");
		int id = userDAO.isValid(nick, password);
		if (id == 0) {
			response.sendError(401, "Invalid login or password");
			return false;
		} else {	
			return true;
		}
	}
}


