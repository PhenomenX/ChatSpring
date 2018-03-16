package com.epam.chatspring.dao.datalayer.xml.dom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.epam.chatspring.dao.datalayer.data.Message;
import com.epam.chatspring.dao.datalayer.data.Status;
import com.epam.chatspring.dao.datalayer.data.User;

public class DOMHandler {

	Document doc;

	public void generateDoc() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File("resource/OnlineChat.xml"));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addNewMessage(Message message) {
		if (doc == null) {
			generateDoc();
		}
		Element root = doc.getDocumentElement();
		Element messages = (Element) root.getChildNodes().item(1);

		Element newMessage = doc.createElement("Message");
		Element role = doc.createElement("Role");
		Element status = doc.createElement("Status");
		Element name = doc.createElement("name");
		Element text = doc.createElement("text");

		role.setTextContent(message.getUser().getRole().name());
		status.setTextContent(message.getUser().getStatus().name());
		name.setTextContent(message.getUser().getName());
		text.setTextContent(message.getMessage());
		newMessage.setAttribute("Time_Stamp", message.getDate().toString());

		newMessage.appendChild(role);
		newMessage.appendChild(name);
		newMessage.appendChild(text);
		newMessage.appendChild(status);

		messages.appendChild(newMessage);
		writeDocument();
	}

	public void writeDocument() {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new FileWriter(
					"resource/OnlineChat.xml"));
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	public void changeStatus(Status status, User user) {
		user.setStatus(status);
		Timestamp date = new Timestamp(System.currentTimeMillis());
		String text = "";
		switch (status) {
		case LOGOUT:
			text = user.getName() + " logged out";
			break;
		case LOGIN:
			text = user.getName() + " logged in";
			break;
		
		}
		Message message = new Message(date, user, text);
		addNewMessage(message);
	}

	public void kickUser(User admin, User kickableUser) {
		kickableUser.setStatus(Status.KICK);
		Timestamp date = new Timestamp(System.currentTimeMillis());
		String text = admin.getName() + " kicked " + kickableUser.getName();
		Message message = new Message(date, kickableUser, text);
		addNewMessage(message);
	}

}
