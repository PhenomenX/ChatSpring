package com.epam.chatspring.exceptions;

public class UserNotExistException extends Exception{

	public UserNotExistException(String message){
		super(message);
	}
}
