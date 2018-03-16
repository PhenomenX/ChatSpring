package com.epam.chatspring.dao.datalayer.oracledb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.epam.chatspring.dao.ResourceManager;
import com.epam.chatspring.dao.datalayer.data.Message;
import com.epam.chatspring.dao.datalayer.data.Role;
import com.epam.chatspring.dao.datalayer.data.Status;
import com.epam.chatspring.dao.datalayer.data.User;

public class OracleDBHandler {

	private static String messageQ = ResourceManager.getRegExp("newMessage");
	private static String lastQ = ResourceManager.getRegExp("last");
	private static String isLoginQ = ResourceManager.getRegExp("isLogin");
	private static String isKickedQ = ResourceManager.getRegExp("isKicked");
	private static String allLoggedQ = ResourceManager.getRegExp("allLogged");
	private static String roleQ = ResourceManager.getRegExp("role");
	private static Role roles[] = Role.values();
	private static Status statuses[] = Status.values();
	private static String allKickedQ = ResourceManager.getRegExp("allKicked");

	public static void sendMessage(Message message, Connection connection) {
		try {
			PreparedStatement ps = connection.prepareStatement(messageQ);
			ps.setInt(1, message.getUser().getRole().ordinal() + 1);
			ps.setInt(2, message.getUser().getStatus().ordinal() + 1);
			ps.setString(3, message.getUser().getName());
			ps.setString(4, message.getMessage());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Message> getLast(int count, Connection connection) {
		List<Message> messages = new ArrayList<Message>();
		try {
			PreparedStatement ps = connection.prepareStatement(lastQ);
			ps.setInt(1, count);
			Message message;
			String name;
			Role role;
			Status status;
			String text;
			Timestamp data;

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				role = roles[rs.getInt("ROLE_ID") - 1];
				status = statuses[rs.getInt(3) - 1];
				name = rs.getString(4);
				data = rs.getTimestamp(5);
				text = rs.getString(6);
				message = new Message(data, new User(name, status, role), text);
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Collections.reverse(messages);
		return messages;
	}

	public static boolean isLogged(User user, Connection connection) {
		boolean isLogged = false;
		try {
			// PreparedStatement ps = connection.prepareStatement(isLoginQ);
			// ps.setString(1, user.getName());
			Statement st = connection.createStatement();
			String query = String.format(isLoginQ, user.getName());
			ResultSet rs = st.executeQuery(query);
			// ResultSet rs = st.executeQuery();

			while (rs.next()) {
				isLogged = Boolean.valueOf(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isLogged;
	}

	public static boolean isKicked(User user, Connection connection) {
		boolean isKicked = false;
		try {
			// PreparedStatement ps = connection.prepareStatement(isKickedQ);
			// ps.setString(1, user.getName());
			Statement st = connection.createStatement();
			String query = String.format(isKickedQ, user.getName());
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				isKicked = Boolean.valueOf(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isKicked;
	}

	public static List<User> getAllLogged(Connection connection) {
		List<User> onlineUserList = new ArrayList<User>();
		User user;
		Role role;
		Status status;
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(allLoggedQ);
			while (rs.next()) {
				role = roles[rs.getInt(2) - 1];
				status = statuses[rs.getInt(3) - 1];
				user = new User(rs.getString(1), status, role);
				onlineUserList.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return onlineUserList;
	}

	public static Role getRole(String nick, Connection connection) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Role role = null;
		try {
			// ps = connection.prepareStatement(roleQ);
			// ps.setString(1, nick);
			// resSet = ps.executeQuery();
			Statement st = connection.createStatement();
			String query = String.format(roleQ, nick);
			rs = st.executeQuery(query);
			rs.next();
			String roleString = rs.getString("NAME").trim();
			role = Role.valueOf(roleString);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (role == null) {
			System.err.print("This user does not exist");
		}
		return role;
	}

	public static List<User> getAllKicked(Connection connection) {
		List<User> kickedUserList = new ArrayList<User>();
		User user;
		Role role;
		Status status;
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(allKickedQ);
			while (rs.next()) {
				role = roles[rs.getInt(2) - 1];
				status = statuses[rs.getInt(3) - 1];
				user = new User(rs.getString(1), status, role);
				kickedUserList.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return kickedUserList;
	}

}
