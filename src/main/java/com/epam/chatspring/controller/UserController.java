package com.epam.chatspring.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.epam.chatspring.filter.AuthorizationInterceptor;
import com.epam.chatspring.model.Status;
import com.epam.chatspring.model.User;
import com.epam.chatspring.service.AdminService;
import com.epam.chatspring.service.FileStoreService;
import com.epam.chatspring.service.UserService;

@RestController
public class UserController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private FileStoreService fileStoreService;

	private static final Logger logger = Logger.getLogger(AuthorizationInterceptor.class);
	
	@RequestMapping(value = "/users/register", method = RequestMethod.POST)
	@ResponseBody
	public void register(@RequestParam MultipartFile file, @RequestParam(required = false) String nick,
			@RequestParam(required = false) String password) {
		User user = new User(nick, password);
		logger.info(String.format("User %s register", nick));
		user.setPicturePath(file.getOriginalFilename());
		userService.register(user);
		fileStoreService.saveFile(file);
	}

	@RequestMapping(value = "/users/login", method = RequestMethod.PUT)
	@ResponseBody
	public User login(@RequestParam String nick, @RequestParam String password, HttpSession httpSession) {
		User user = new User(nick, password);
		MDC.put("userName", nick);
		logger.info("User login");
		user = userService.login(user, httpSession);
		return user;
	}

	@RequestMapping(value = "/users/logout", method = RequestMethod.PUT)
	@ResponseBody
	public void logout(HttpSession httpSession) {
		logger.info("User logout");
		User user = (User) httpSession.getAttribute("currentUser");
		if (user != null) {
			userService.logout(user, httpSession);
		}
	}

	@RequestMapping(value = "/users/{nick}", method = RequestMethod.GET)
	@ResponseBody
	public User getUser(@PathVariable String nick) {
		logger.info(String.format("User get info about %s", nick));
		return userService.getUser(nick);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getUsers(@RequestParam(required = false) String status) {
		List<User> users = userService.getUsers(Status.valueOf(status));
		return users;
	}

	@RequestMapping(value = "/users/kick", method = RequestMethod.PUT)
	@ResponseBody
	public void kick(@RequestParam String nick, HttpSession httpSession) {
		logger.info(String.format("Admin kicked user %s", nick));
		adminService.kick(nick, httpSession);
	}

	@RequestMapping(value = "/users/unkick", method = RequestMethod.PUT)
	@ResponseBody
	public void unkick(@RequestParam String nick, HttpSession httpSession) {
		logger.info(String.format("Admin unkicked user %s", nick));
		adminService.unkick(nick, httpSession);
	}

}
