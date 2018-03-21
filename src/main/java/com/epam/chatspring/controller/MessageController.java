package com.epam.chatspring.controller;

import com.epam.chatspring.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

public class MessageController {
	
	@RequestMapping(value = "/messages", method = RequestMethod.GET)
    @ResponseBody
    public String getMessages() {
        return "Welcome to RestTemplate Example.";
    }
	
	@RequestMapping(value = "/messages", method = RequestMethod.POST)
    @ResponseBody
    public String sendMessage() {
        return "Welcome to RestTemplate Example.";
    }

}
