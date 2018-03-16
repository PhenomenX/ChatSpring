package com.epam.chatspring.dao.datalayer.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.epam.chatspring.dao.datalayer.xml.dom.DOMHandler;
import com.epam.chatspring.dao.datalayer.xml.sax.SAXHandlerMessage;
import com.epam.chatspring.dao.datalayer.MessageDAO;
import com.epam.chatspring.dao.datalayer.data.Message;

public class XMLMessageDAO implements MessageDAO {
	
	@Override
	public List<Message> getLast(int count) {
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
		messages = messages.subList(messages.size() - count, messages.size());
		return messages;
	}

	@Override
	public void sendMessage(Message message) {
		DOMHandler handler = new DOMHandler();
		handler.addNewMessage(message);
	}



}
