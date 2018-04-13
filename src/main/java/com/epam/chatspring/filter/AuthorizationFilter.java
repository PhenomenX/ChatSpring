package com.epam.chatspring.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.chatspring.dao.datalayer.DAOFactory;
import com.epam.chatspring.dao.datalayer.DBType;
import com.epam.chatspring.dao.datalayer.MessageDAO;
import com.epam.chatspring.dao.datalayer.UserDAO;

public class AuthorizationFilter implements Filter {
	private DAOFactory factory;
	private MessageDAO messageDAO;
	private UserDAO userDAO;

	@Override
	public void destroy() {
		factory = null;
		messageDAO = null;
		userDAO = null;

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String nick = httpRequest.getParameter("nick");
		String password = httpRequest.getParameter("password");
		int id = userDAO.isValid(nick, password);
		if (id == 0) {
			httpResponse.sendError(401, "Invalid login or password");
		} else {
			chain.doFilter(httpRequest, httpResponse);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		factory = DAOFactory.getInstance(DBType.ORACLE);
		messageDAO = factory.getMessageDAO();
		userDAO = factory.getUserDAO();
	}

}
