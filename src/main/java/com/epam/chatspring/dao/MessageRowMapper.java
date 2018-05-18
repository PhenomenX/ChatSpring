package com.epam.chatspring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.chatspring.model.Message;

public class MessageRowMapper implements RowMapper<Message>  {

	@Override
	public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
		Message message = new Message();
		message.setId(rs.getInt(1));
		message.setNick(rs.getString(2));
		message.setDate(rs.getTimestamp(3));
		message.setMessage(rs.getString(4));
		return message;
	}

}
