package com.epam.chatspring.dao;

public abstract class DAOFactory {
	public static DAOFactory getInstance(DBType dbType) {
		DAOFactory result = dbType.getDAOFactory();
		return result;
	}

	public abstract MessageDAO getMessageDAO();

	public abstract UserDAO getUserDAO();
}