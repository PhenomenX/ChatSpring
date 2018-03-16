package com.epam.chatspring.dao.datalayer.xml;

import com.epam.chatspring.dao.datalayer.DAOFactory;
import com.epam.chatspring.dao.datalayer.MessageDAO;
import com.epam.chatspring.dao.datalayer.UserDAO;

public class XMLDAOFactory extends DAOFactory {
	

	@Override
	public MessageDAO getMessageDAO() {
		return new XMLMessageDAO();
	}

	@Override
	public UserDAO getUserDAO() {
		return new XMLUserDAO();
	}

}
