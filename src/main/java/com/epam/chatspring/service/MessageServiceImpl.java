package com.epam.chatspring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.chatspring.dao.datalayer.DAOFactory;
import com.epam.chatspring.dao.datalayer.DBType;
import com.epam.chatspring.dao.datalayer.MessageDAO;
import com.epam.chatspring.dao.datalayer.UserDAO;
import com.epam.chatspring.dao.datalayer.data.Message;

@Service(value = "messageService")
public class MessageServiceImpl implements MessageService {
	
	private DAOFactory factory;
	private MessageDAO messageDAO;
	
	public MessageServiceImpl(){
		this.factory = DAOFactory.getInstance(DBType.ORACLE);
		this.messageDAO = factory.getMessageDAO();
	}

	@Override
	public void sendMessage(Message message) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Message> getMessages() {
		return messageDAO.getLast(5);
	}

}
