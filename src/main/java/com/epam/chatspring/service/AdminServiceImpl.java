package com.epam.chatspring.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epam.chatspring.dao.MessageDAO;
import com.epam.chatspring.dao.UserDAO;
import com.epam.chatspring.model.Message;
import com.epam.chatspring.model.User;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private MessageDAO messageDAO;
	
	@Value( "${message.kick}" )
	String kickMessage;
	
	@Value( "${message.unkick}" )
	String unkickMessage;

	@Override
	public void kick(String nick, HttpSession httpSession) {
		userDAO.kick(nick);
		User admin = (User) httpSession.getAttribute("currentUser");
		String formattedKickMessage = String.format(kickMessage, nick);
		Message message = new Message(admin.getName(), formattedKickMessage);
		messageDAO.sendMessage(message);
	}

	@Override
	public void unkick(String nick, HttpSession httpSession) {
		userDAO.unkick(nick);
		User admin = (User) httpSession.getAttribute("currentUser");
		String formattedKickMessage = String.format(unkickMessage, nick);
		Message message = new Message(admin.getName(), formattedKickMessage);
		messageDAO.sendMessage(message);
	}

}
