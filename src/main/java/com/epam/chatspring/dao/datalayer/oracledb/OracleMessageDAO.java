package com.epam.chatspring.dao.datalayer.oracledb;

import java.sql.Connection;
import java.util.List;

import com.epam.chatspring.dao.datalayer.MessageDAO;
import com.epam.chatspring.dao.datalayer.data.Message;

public class OracleMessageDAO implements MessageDAO {

	private Connection connection;

	public OracleMessageDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Message> getLast(int count) {
		List<Message> messages = OracleDBHandler.getLast(count, connection);
		return messages;
	}

	@Override
	public void sendMessage(Message message) {
		OracleDBHandler.sendMessage(message, connection);
			}
	
	

}
