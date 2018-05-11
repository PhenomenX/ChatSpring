package com.epam.chatspring.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.epam.chatspring.exceptions.AccessPrivilegeException;
import com.epam.chatspring.exceptions.NameValidationException;
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
	
	@Autowired
	private User currentUser;
	
	@Value("${message.error.authentication.admin}")
	private String grantErrorMessage;
	
	@Value("${message.error.registration}")
	private String registrationErrorMessage;

	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@RequestMapping(value = "/users/register", method = RequestMethod.POST)
	@ResponseBody
	public void register(@RequestParam MultipartFile file, @RequestParam(required = false) String nick,
			@RequestParam(required = false) String password) throws NameValidationException {
		User user = new User(nick, password);
		logger.info(String.format("User %s register", nick));
		user.setPicturePath(file.getOriginalFilename());
		userService.register(user);
		fileStoreService.saveFile(file);
	}



	@RequestMapping(value = "/users/logout", method = RequestMethod.PUT)
	@ResponseBody
	public void logout(HttpSession httpSession) {
		logger.info("User logout");
		userService.logout(currentUser);
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
	public void kick(@RequestBody String nick, HttpSession httpSession) throws AccessPrivilegeException {
		logger.info(String.format("Admin kicked user %s", nick));
		if(currentUser.isAdmin()){
			logger.debug("User is admin");
			adminService.kick(nick);
		} else{
			throw new AccessPrivilegeException(grantErrorMessage);
		}
	}

	@RequestMapping(value = "/users/unkick", method = RequestMethod.PUT)
	@ResponseBody
	public void unkick(@RequestBody String nick) throws AccessPrivilegeException {
		logger.info(String.format("Admin unkicked user %s", nick));
		if(currentUser.isAdmin()){
			logger.debug("User is admin");
			adminService.unkick(nick);
		} else{
			throw new AccessPrivilegeException(grantErrorMessage);
		}
	}
	
	@ExceptionHandler(value = { AccessPrivilegeException.class })
	protected void handlePrivelegeConflict(HttpServletResponse response) throws IOException {
		logger.debug("User not admin. Send response with error");
		response.setHeader("message", grantErrorMessage);
		response.sendError(HttpStatus.FORBIDDEN.value(), grantErrorMessage);
	}

	@ExceptionHandler(value = { NameValidationException.class })
	protected void handleNameValidationConflict(HttpServletResponse response) throws IOException {
		logger.debug("Registration failed. " + registrationErrorMessage);
		response.setHeader("message", grantErrorMessage);
		response.sendError(HttpStatus.NOT_ACCEPTABLE.value(), registrationErrorMessage);
	}
}
