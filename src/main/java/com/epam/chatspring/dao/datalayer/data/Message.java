package com.epam.chatspring.dao.datalayer.data;

import java.sql.Timestamp;

public class Message {
	private Timestamp data;
	private User user;
	private String message;
	
	public Message(Timestamp data, User user, String message){
		this.data = data;
		this.message = message;
		this.user = user;		
	}
	
	public Message(User user, String message){
		this.user = user;
		this.message = message;
	}

	public Timestamp getDate() {
		return this.data;
	}

	public void setDate(Timestamp data) {
		this.data = data;
	}

	public User getUser() {
		return this.user;
	}
	
	public String getMessage(){
		return this.message;
	}

	public String toString() {
		StringBuilder userLogString = new StringBuilder();
		userLogString.append(data);
		userLogString.append("\t");
		userLogString.append(user);
		userLogString.append("\t");
		userLogString.append(message);
		return userLogString.toString();
	}
}
