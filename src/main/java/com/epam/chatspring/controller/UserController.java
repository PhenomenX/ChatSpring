package com.epam.chatspring.controller;

import com.epam.chatspring.dao.*;
import com.epam.chatspring.dao.datalayer.data.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public String register() {
        return "Welcome to RestTemplate Example.";
    }
	
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
    @ResponseBody
    public String login() {
        return "Welcome to RestTemplate Example.";
    }
	
	@RequestMapping(value = "/users/logout/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String logout() {
        return "Welcome to RestTemplate Example.";
    }
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getUser() {
        return "Welcome to RestTemplate Example.";
    }
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		users.add(new User("Psycho"));
		users.add(new User("Pennywise"));
        return users;
    }
	
	@RequestMapping(value = "/users/kick/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String kick() {
        return "Welcome to RestTemplate Example.";
    }
	
	@RequestMapping(value = "/users/unkick/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String unkick() {
        return "Welcome to RestTemplate Example.";
    }
	
	

}
