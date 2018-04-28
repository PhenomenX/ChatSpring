package com.epam.chatspring.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.chatspring.model.Message;
import com.epam.chatspring.model.User;
import com.epam.chatspring.service.MessageService;

@RestController
public class MessageController {

	@Autowired
	@Qualifier("messageService")
	MessageService messageService;

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
	public void sendMessage(@RequestParam String messageText, HttpSession httpSession,
			HttpServletResponse httpResponse) {
		Timestamp date = new Timestamp(System.currentTimeMillis());
		User user = (User) httpSession.getAttribute("currentUser");
		if (user == null) {
			try {
				httpResponse.sendError(403, "User is lost, login please");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Message message = new Message(date, user.getName(), messageText);
			messageService.sendMessage(message);
			
		}
	}

}
