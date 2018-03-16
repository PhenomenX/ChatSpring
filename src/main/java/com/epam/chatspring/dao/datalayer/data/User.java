package com.epam.chatspring.dao.datalayer.data;

public class User {
	private String name;
	private Status status;
	private Role role;

	public User(String name) {
		this.name = name;
	}

	public User(String name, Status status, Role role) {
		this.name = name;
		this.status = status;
		this.role = role;
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
