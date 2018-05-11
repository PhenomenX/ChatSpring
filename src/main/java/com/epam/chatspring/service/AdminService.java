package com.epam.chatspring.service;

import javax.servlet.http.HttpSession;

public interface AdminService {
	void unkick(String nick);

	void kick(String nick);

}
