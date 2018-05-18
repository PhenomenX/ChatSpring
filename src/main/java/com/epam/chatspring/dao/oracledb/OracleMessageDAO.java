package com.epam.chatspring.dao.oracledb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import com.epam.chatspring.dao.MessageDAO;
import com.epam.chatspring.dao.MessageRowMapper;
import com.epam.chatspring.model.Message;

public class OracleMessageDAO implements MessageDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Value("${getMessages}")
	private String lastQ;
	@Value("${newMessage}")
	private String messageQ;

	public OracleMessageDAO() {
		super();
	}

	@Override
	public List<Message> getLast(int count) {
		List<Message> messages = jdbcTemplate.query(lastQ, new Object[] { count },
				new MessageRowMapper());
		return messages;
	}

	@Override
	public void sendMessage(Message message, int userID) {
		System.out.println(message.getType().ordinal());
		jdbcTemplate.update(messageQ,
				new Object[] { userID, message.getMessage(), message.getType().ordinal() });
	}
}
