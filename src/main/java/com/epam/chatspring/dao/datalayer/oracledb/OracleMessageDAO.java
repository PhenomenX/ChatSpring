package com.epam.chatspring.dao.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.epam.chatspring.dao.ResourceManager;
import com.epam.chatspring.dao.datalayer.MessageDAO;
import com.epam.chatspring.dao.datalayer.data.Message;
import com.epam.chatspring.dao.datalayer.data.Role;
import com.epam.chatspring.dao.datalayer.data.Status;
import com.epam.chatspring.dao.datalayer.data.User;

public class OracleMessageDAO implements MessageDAO {

	private Connection connection;
	private String lastQ;

	public OracleMessageDAO(Connection connection) {
		this.connection = connection;
		this.lastQ = ResourceManager.getRegExp("last");
	}

	@Override
	public List<Message> getLast(int count) {
		List<Message> messages = new ArrayList<Message>();
		try {
			PreparedStatement ps = connection.prepareStatement(lastQ);
			ps.setInt(1, count);
			Message message;
			String name;
			Role role;
			Status status;
			String text;
			Timestamp data;

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				role = Role.values()[rs.getInt("ROLE_ID") - 1];
				status = Status.values()[rs.getInt(3) - 1];
				name = rs.getString(4);
				data = rs.getTimestamp(5);
				text = rs.getString(6);
				message = new Message(data, new User(name, status, role), text);
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Collections.reverse(messages);
		return messages;
	}

	@Override
	public void sendMessage(Message message) {
		OracleDBHandler.sendMessage(message, connection);
			}
	
	

}
