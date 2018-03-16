package com.epam.chatspring.dao.datalayer;

import java.util.List;

import com.epam.chatspring.dao.datalayer.data.Message;

public interface MessageDAO {
	List<Message> getLast(int count);

	void sendMessage(Message message);
}
