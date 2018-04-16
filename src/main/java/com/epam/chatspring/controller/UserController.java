package com.epam.chatspring.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.epam.chatspring.dao.datalayer.data.Status;
import com.epam.chatspring.dao.datalayer.data.User;
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

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ResponseBody
	public void register(@RequestParam MultipartFile file, @RequestParam(required = false) String nick,
			@RequestParam(required = false) String password) {
		User user = new User(nick, password);
		user.setPicturePath(file.getOriginalFilename());
		userService.register(user);
		fileStoreService.saveFile(file);
	}

	@RequestMapping(value = "/users/login", method = RequestMethod.PUT)
	@ResponseBody
	public String login(@RequestParam String nick, @RequestParam String password, HttpSession httpSession) {
		User user = new User(nick, password);
		String role = userService.login(user, httpSession).toString();
		return role;
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
	public List<User> getUsers(@RequestParam String status) {
		List<User> users = userService.getUsers(Status.valueOf(status));
		return users;
	}

	@RequestMapping(value = "/users/kick", method = RequestMethod.PUT)
	@ResponseBody
	public void kick(@RequestParam String nick) {
		adminService.kick(nick);
	}

	@RequestMapping(value = "/users/unkick/", method = RequestMethod.PUT)
	@ResponseBody
	public void unkick(@RequestParam String nick) {
		adminService.unkick(nick);
	}

}
