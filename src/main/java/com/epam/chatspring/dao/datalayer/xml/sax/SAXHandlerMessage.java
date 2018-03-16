package com.epam.chatspring.dao.datalayer.xml.sax;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.epam.chatspring.dao.datalayer.data.Message;
import com.epam.chatspring.dao.datalayer.data.Role;
import com.epam.chatspring.dao.datalayer.data.Status;
import com.epam.chatspring.dao.datalayer.data.User;



public class SAXHandlerMessage extends DefaultHandler {

	private List<Message> messages = new ArrayList<Message>();
	private Message message;
	private Timestamp date;
	private User user;
	private String userName;
	private Status status;
	private String text;
	private Role role;
	private String element;

	@Override
	public void startDocument() throws SAXException {
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		element = qName;
		if ("Message".equals(element)) {
			date = Timestamp.valueOf(attributes.getValue("Time_Stamp"));
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if ("Role".equals(element)) {
			role = Role.valueOf(new String(ch, start, length));
		}
		if ("Status".equals(element)) {
			status = Status.valueOf(new String(ch, start, length));
		}
		if ("name".equals(element)) {
			userName = new String(ch, start, length);
		}
		if ("text".equals(element)) {
			text = new String(ch, start, length);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		element = qName;
		if ("Message".equals(element)) {
			user = new User(userName, status, role);
			message = new Message(date, user, text);
			messages.add(message);
		}
		element = "";
	}

	@Override
	public void endDocument() throws SAXException {
	}

	public List<Message> getMessages() {
		return messages;
	}

}
