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
	@Value("SELECT * from (SELECT m.MESSAGE_ID, u.NICK, m.TIME_STAMP, m.MESSAGE FROM MESSAGES"
			+ " m JOIN USERS u ON m.USER_ID = u.USER_ID ORDER BY MESSAGE_ID DESC)"
			+ " WHERE ROWNUM<=? ORDER BY MESSAGE_ID")
	private String lastQ;
	@Value("INSERT INTO MESSAGES VALUES (id_seq.nextval,?,SYSTIMESTAMP,?,?)")
	private String messageQ;

	public OracleMessageDAO() {
		super();
	}

	@Override
	public List<Message> getLast(int count) {
		List<Message> messages = jdbcTemplate.query(lastQ, new Object[] { count }, new MessageRowMapper());
		return messages;
	}

	@Override
	public void sendMessage(Message message, int userID) {
		jdbcTemplate.update(messageQ, new Object[] { userID, message.getMessage(), message.getType().ordinal() });
	}
}
