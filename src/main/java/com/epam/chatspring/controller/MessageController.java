package com.epam.chatspring.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.chatspring.model.Message;
import com.epam.chatspring.model.MessageType;
import com.epam.chatspring.model.User;
import com.epam.chatspring.service.MessageService;

@RestController
public class MessageController {

	@Autowired
	@Qualifier("messageService")
	MessageService messageService;
	
	@Autowired
	private User currentUser;

	private static final Logger logger = Logger.getLogger(MessageController.class);

	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getMessages() {
		List<String> messagesS = new ArrayList<String>();
		List<Message> messages = messageService.getMessages();
		for (Message message : messages) {
			messagesS.add(message.toString());
		}
		return messagesS;
	}

	@RequestMapping(value = "/messages", method = RequestMethod.POST)
	@ResponseBody
	public void sendMessage(@RequestBody String messageText, HttpSession httpSession,
			HttpServletResponse httpResponse) {
		logger.info(String.format("User send message \"%s\"", messageText));
		Timestamp date = new Timestamp(System.currentTimeMillis());
		Message message = new Message(date, currentUser.getName(), messageText);
		message.setType(MessageType.COMMON);
		messageService.sendMessage(message);
	}

}
