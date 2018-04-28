package com.epam.chatspring.model;

public class User {
	private String name;
	private String password;
	private String picturePath;
	private Status status;
	private Role role;
	private int id;

	public User(String name) {
		this.name = name;
	}

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public User(String name, Status status, Role role) {
		this.name = name;
		this.status = status;
		this.role = role;
	}

	public User(String name, Status status, Role role, String picture) {
		this.name = name;
		this.status = status;
		this.role = role;
		this.picturePath = picture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String toString() {
		StringBuilder userString = new StringBuilder();
		userString.append(role);
		userString.append(" ");
		userString.append(name);
		userString.append(" ");
		userString.append(status);
		return userString.toString();
	}



}
