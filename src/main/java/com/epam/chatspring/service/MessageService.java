package com.epam.chatspring.service;

import java.util.List;

import com.epam.chatspring.model.Message;

public interface MessageService {
	
	void sendMessage(Message message);
	List<Message> getMessages();

}
