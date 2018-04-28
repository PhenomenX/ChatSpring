package com.epam.chatspring.model;

import java.sql.Timestamp;

public class Message {
	private Timestamp date;
	private String nick;
	private String message;
	
	public Message(Timestamp date, String user, String message){
		this.date = date;
		this.message = message;
		this.nick = user;		
	}
	
	public Message(String user, String message){
		this.nick = user;
		this.message = message;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp data) {
		this.date = data;
	}

	public String getNick() {
		return this.nick;
	}
	
	public String getMessage(){
		return this.message;
	}

	public String toString() {
		StringBuilder userLogString = new StringBuilder();
		userLogString.append(date);
		userLogString.append("\t");
		userLogString.append(nick);
		userLogString.append("\t");
		userLogString.append(message);
		return userLogString.toString();
	}
}
