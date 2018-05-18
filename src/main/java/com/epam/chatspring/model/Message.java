package com.epam.chatspring.model;

import java.sql.Timestamp;

public class Message {

	private int id;
	private Timestamp date;
	private MessageType type;
	private String nick;
	private String message;

	public Message() {
		super();
	}

	public Message(Timestamp date, String user, String message) {
		this.date = date;
		this.message = message;
		this.nick = user;
	}

	public Message(String user, String message) {
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

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
