package com.epam.chatspring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.chatspring.dao.datalayer.MessageDAO;
import com.epam.chatspring.dao.datalayer.data.Message;

@Service(value = "messageService")
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageDAO messageDAO;


	@Override
	public void sendMessage(Message message) {
		messageDAO.sendMessage(message);
	}

	@Override
	public List<Message> getMessages() {
		return messageDAO.getLast(5);
	}

}
