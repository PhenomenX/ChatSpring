package com.epam.chatspring.dao.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.epam.chatspring.dao.MessageDAO;
import com.epam.chatspring.dao.ResourceManager;
import com.epam.chatspring.model.Message;

public class OracleMessageDAO implements MessageDAO {

	private Connection connection;
	private String lastQ;
	private String messageQ;

	public OracleMessageDAO(Connection connection) {
		this.connection = connection;
		this.lastQ = ResourceManager.getRegExp("last");
		this.messageQ = ResourceManager.getRegExp("newMessage");
	}

	@Override
	public List<Message> getLast(int count) {
		List<Message> messages = new ArrayList<Message>();
		try(PreparedStatement ps = connection.prepareStatement(lastQ)) {
			ps.setInt(1, count);
			Message message;
			String name;
			String text;
			Timestamp data;

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				name = rs.getString(2);
				data = rs.getTimestamp(3);
				text = rs.getString(4);
				message = new Message(data, name, text);
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
		try {
			PreparedStatement ps = connection.prepareStatement(messageQ);
			ps.setString(1, message.getNick());
			ps.setString(2, message.getMessage());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
