package com.epam.chatspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epam.chatspring.dao.datalayer.UserDAO;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserDAO userDAO;
	
	@Value( "${message.kick}" )
	String loginMessage;
	
	@Value( "${message.unkick}" )
	String logoutMessage;

	@Override
	public void kick(String nick) {
		userDAO.kick(nick);
	}

	@Override
	public void unkick(String nick) {
		userDAO.unkick(nick);
	}

}
