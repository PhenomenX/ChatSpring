package com.epam.chatspring.dao.datalayer;

import java.sql.SQLException;

import com.epam.chatspring.dao.datalayer.oracledb.OracleDBDAOFactory;
import com.epam.chatspring.dao.datalayer.xml.XMLDAOFactory;

public enum DBType {
	XML {
		@Override
		public DAOFactory getDAOFactory() {
			DAOFactory xmlDAOFactory = new XMLDAOFactory();
			return xmlDAOFactory;
		}
	},
	ORACLE {
		@Override
		public DAOFactory getDAOFactory() {
			DAOFactory oracleDBDAOFactory = null;
			try {
				oracleDBDAOFactory = OracleDBDAOFactory.getInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return oracleDBDAOFactory;
		}
	};

	public static DBType getTypeByName(String dbType) {
		try {
			return DBType.valueOf(dbType.toUpperCase());
		} catch (Exception e) {
			throw new DBTypeException();
		}
	}

	public abstract DAOFactory getDAOFactory();

}
