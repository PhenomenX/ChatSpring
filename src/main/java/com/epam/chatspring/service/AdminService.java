package com.epam.chatspring.service;

import com.epam.chatspring.dao.datalayer.data.User;

public interface AdminService {
	
	void kick(User user);
	void unkick(User user);

}
