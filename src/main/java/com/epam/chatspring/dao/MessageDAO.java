package com.epam.chatspring.dao;

import java.util.List;

import com.epam.chatspring.model.Message;

public interface MessageDAO {
	List<Message> getLast(int count);

	void sendMessage(Message message, int userID);
}
