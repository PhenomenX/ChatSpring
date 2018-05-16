package com.epam.chatspring.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.chatspring.dao.UserDAO;
import com.epam.chatspring.exceptions.UserKickedException;
import com.epam.chatspring.exceptions.UserNotExistException;
import com.epam.chatspring.model.User;
import com.epam.chatspring.service.UserService;

@RestController
public class LoginController {

	@Autowired
	UserDAO userDAO;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	User currentUser;

	@Value("${message.error.login}")
	private String loginErrorMessage;

	@Value("${message.error.login.kick}")
	private String kickedUserMessage;

	private static final Logger logger = Logger.getLogger(LoginController.class);

	@RequestMapping(value = "/users/login", method = RequestMethod.PUT)
	@ResponseBody
	public User login(@RequestBody User user, HttpSession httpSession)
			throws IOException, UserNotExistException, UserKickedException {
		MDC.put("userName", user.getName());
		logger.debug("User trying to login");
		if (!userDAO.isValid(user)) {
			logger.debug("User wasn't login. " + loginErrorMessage);
			throw new UserNotExistException(loginErrorMessage);
		} else {
			if (userDAO.isKicked(user)) {
				logger.debug("User wasn't login" + kickedUserMessage);
				throw new UserKickedException(kickedUserMessage);
			} else {
				logger.debug("User is valid");
			}
		}
		logger.info("User login");
		currentUser.setName(user.getName());
		user = userService.login(currentUser);
		return user;
	}

	@ExceptionHandler(value = { UserKickedException.class })
	protected void handleConflict(HttpServletResponse response) throws IOException {
		response.setHeader("message", kickedUserMessage);
		response.sendError(HttpStatus.UNAUTHORIZED.value(), kickedUserMessage);
	}

	@ExceptionHandler(value = { UserNotExistException.class })
	protected void handleConflict1(HttpServletResponse response) throws IOException {
		response.setHeader("message", loginErrorMessage);
		response.sendError(HttpStatus.UNAUTHORIZED.value(), loginErrorMessage);
	}
}
