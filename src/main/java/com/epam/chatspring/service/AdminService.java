package com.epam.chatspring.service;

import javax.servlet.http.HttpSession;

public interface AdminService {
	void unkick(String nick, HttpSession httpSession);

	void kick(String nick, HttpSession httpSession);

}
