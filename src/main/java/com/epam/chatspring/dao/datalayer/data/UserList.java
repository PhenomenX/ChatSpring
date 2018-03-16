package com.epam.chatspring.dao.datalayer.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserList {
	private Map<String, User> users = new HashMap<String, User>();

	public Map<String, User> getUsers() {
		return users;
	}

	public void setUsers(Map<String, User> users) {
		this.users = users;
	}
	
	public void addUser(User user){
		String name = user.getName();
		users.put(name, user);
	}
	
	public List<User> getOnlineUsers() {
		List<User> onlineUsers= new ArrayList<User>();
		Status status;
		for (User user : users.values()) {
			status = user.getStatus();
		    if(status==Status.LOGIN || status==Status.MESSAGE){
		    	onlineUsers.add(user);
		    }
		}
		return onlineUsers;
	}
	
	public String toString(){
		StringBuilder usersString = new StringBuilder();
		for (User user : users.values()) {
		    System.out.println(user);
		}
		return usersString.toString();
	}
	
	public Status getUserStatus(String userName){
		return users.get(userName).getStatus();
	}
	
	public Role getUserRole(String userName){
		return users.get(userName).getRole();
	}

}
