package com.epam.chatspring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epam.chatspring.dao.MessageDAO;
import com.epam.chatspring.model.Message;
import com.epam.chatspring.model.User;

@Service(value = "messageService")
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageDAO messageDAO;

	@Autowired
	private User currentUser;
	
	@Value("${messageCount}")
	private int messageCount;

	@Override
	public void sendMessage(Message message) {
		messageDAO.sendMessage(message, currentUser.getId());
	}

	@Override
	public List<Message> getMessages() {
		return messageDAO.getLast(messageCount);
	}

}
