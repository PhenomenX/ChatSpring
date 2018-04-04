package com.epam.chatspring.controller;

import com.epam.chatspring.dao.*;
import com.epam.chatspring.dao.datalayer.data.User;
import com.epam.chatspring.service.UserService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
//	@RequestMapping(value = "/users", method = RequestMethod.POST)
//    @ResponseBody
//    public String register() {
//        return "Welcome to RestTemplate Example.";
//    }
	
	@RequestMapping(value = "/users/login", method = RequestMethod.PUT)
    @ResponseBody
    public String login(@RequestParam String nick, @RequestParam String password, HttpSession httpSession) {
		User user = new User(nick,password);
		userService.login(user, httpSession);
        return nick + " " + password + " ";
    }
	
	@RequestMapping(value = "/users/logout", method = RequestMethod.PUT)
    @ResponseBody
    public void logout(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("currentUser");
        userService.logout(user, httpSession);
    }
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getUser() {
        return "Welcome to RestTemplate Example.";
    }
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers(HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("currentUser");
		List<User> users = userService.getUsers(user);
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
