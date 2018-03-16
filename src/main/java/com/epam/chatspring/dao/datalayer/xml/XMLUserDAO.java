package com.epam.chatspring.dao.datalayer.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.epam.chatspring.dao.datalayer.data.Message;
import com.epam.chatspring.dao.datalayer.data.Role;
import com.epam.chatspring.dao.datalayer.data.Status;
import com.epam.chatspring.dao.datalayer.data.User;
import com.epam.chatspring.dao.datalayer.data.UserList;
import com.epam.chatspring.dao.datalayer.xml.dom.DOMHandler;
import com.epam.chatspring.dao.datalayer.xml.sax.SAXHandlerMessage;
import com.epam.chatspring.dao.datalayer.UserDAO;

public class XMLUserDAO implements UserDAO {

	private DOMHandler domHandler = new DOMHandler();

	@Override
	public void logIn(User user) { // DOM
		domHandler.changeStatus(Status.LOGIN, user);
	}

	@Override
	public void logOut(User user) { // DOM
		domHandler.changeStatus(Status.LOGOUT, user);
	}

	@Override
	public boolean isLogged(User user) { // SAX
		boolean isLogin = statusCheck(Status.LOGIN, user);
		boolean isMessage = statusCheck(Status.MESSAGE, user);
		return (isLogin || isMessage);
	}

	@Override
	public void kick(User admin, User kickableUser) { // DOM
		domHandler.kickUser(admin, kickableUser);
	}

	@Override
	public void unkick(User user) { // DOM
		domHandler.changeStatus(Status.LOGOUT, user);
	}

	@Override
	public boolean isKicked(User user) { // SAX
		return statusCheck(Status.KICK, user);
	}

	@Override
	public List<User> getAllLogged() { // SAX
		UserList userList = generateUserList();
		List<User> onlineUserList = userList.getOnlineUsers();
		return onlineUserList;
	}

	public boolean statusCheck(Status status, User user) {
		UserList userList = generateUserList();
		boolean statusIsValid;
		statusIsValid = status.equals(userList.getUserStatus(user.getName()));
		return statusIsValid;
	}
	
	public Role getRole(String nick){
		UserList userList = generateUserList();
		return userList.getUserRole(nick);
	}
	
	public UserList generateUserList(){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXHandlerMessage saxHandler = new SAXHandlerMessage();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(new File("resource/OnlineChat.xml"), saxHandler);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Message> messages = saxHandler.getMessages();
		User user;
		UserList userList = new UserList();
		for (Message message : messages) {
			user = message.getUser();
			userList.addUser(user);
		}
		return userList;
	}

	@Override
	public List<User> getAllKicked() {
		return null;
	}

}
