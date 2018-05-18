package com.epam.chatspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epam.chatspring.dao.MessageDAO;
import com.epam.chatspring.dao.UserDAO;
import com.epam.chatspring.model.Message;
import com.epam.chatspring.model.MessageType;
import com.epam.chatspring.model.User;
import com.epam.chatspring.model.UserMap;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private MessageDAO messageDAO;
	
	@Autowired
	private UserMap onlineUsers;

	@Autowired
	private User currentUser;

	@Value( "${message.kick}" )
	String kickMessage;
	@Value( "${message.unkick}" )
	String unkickMessage;

	@Override
	public void kick(String nick) {
		userDAO.kick(nick);
		onlineUsers.remove(nick);
		String formattedKickMessage = String.format(kickMessage, nick);
		Message message = new Message(currentUser.getName(), formattedKickMessage);
		message.setType(MessageType.SYSTEM);
		messageDAO.sendMessage(message, currentUser.getId());
	}

	@Override
	public void unkick(String nick) {
		userDAO.unkick(nick);
		String formattedKickMessage = String.format(unkickMessage, nick);
		Message message = new Message(currentUser.getName(), formattedKickMessage);
		message.setType(MessageType.SYSTEM);
		messageDAO.sendMessage(message, currentUser.getId());
	}

}
