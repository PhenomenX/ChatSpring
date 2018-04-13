package com.epam.chatspring.dao.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import com.epam.chatspring.dao.datalayer.UserDAO;
import com.epam.chatspring.dao.datalayer.data.Message;
import com.epam.chatspring.dao.datalayer.data.Role;
import com.epam.chatspring.dao.datalayer.data.Status;
import com.epam.chatspring.dao.datalayer.data.User;
import com.epam.chatspring.dao.ResourceManager;

public class OracleUserDAO implements UserDAO {
	private Connection connection;
	private String updateUserQ;
	private String isLoginQ;
	private String isKickedQ;
	private String isValidQ;
	private String allLoggedQ;
	private String roleQ;
	private String allKickedQ;
	private String newUserQ;

	public OracleUserDAO(Connection connection) {
		this.connection = connection;
		this.updateUserQ = ResourceManager.getRegExp("updateUser");
		this.isValidQ = ResourceManager.getRegExp("isValid");
		this.allLoggedQ = ResourceManager.getRegExp("allLogged");
		this.roleQ = ResourceManager.getRegExp("role");
		this.newUserQ = ResourceManager.getRegExp("newUser");
	}

	@Override
	public void logIn(User user) {
		try (PreparedStatement ps = connection.prepareStatement(updateUserQ)) {
			ps.setInt(1, Status.LOGIN.ordinal() + 1);
			ps.setInt(2, user.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void logOut(User user) {
		try (PreparedStatement ps = connection.prepareStatement(updateUserQ)) {
			ps.setInt(1, Status.LOGOUT.ordinal() + 1);
			ps.setInt(2, user.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isLogged(User user) {
		return OracleDBHandler.isLogged(user, connection);
	}

	@Override
	public void kick(User admin, User kickableUser) {
		// Formatter formatter = new Formatter();
		// formatter.format(ResourceManager.getRegExp("kickF"), admin.getName(),
		// kickableUser.getName());
		// formatter.close();
		// kickableUser.setStatus(Status.KICK);
		// Message message = new Message(kickableUser, formatter.toString());
		// formatter.close();
		// OracleDBHandler.sendMessage(message, connection);

	}

	@Override
	public void unkick(User user) {
		// Formatter formatter = new Formatter();
		// formatter.format(ResourceManager.getRegExp("unkickF"),
		// user.getName());
		// user.setStatus(Status.LOGOUT);
		// Message message = new Message(user, formatter.toString());
		// formatter.close();
		// OracleDBHandler.sendMessage(message, connection);
	}

	@Override
	public boolean isKicked(User user) {
		return OracleDBHandler.isKicked(user, connection);
	}

	@Override
	public List<User> getAllLogged() {
		List<User> onlineUserList = new ArrayList<User>();
		User user;
		Role role;
		Status status;
		try (Statement st = connection.createStatement();) {
			ResultSet rs = st.executeQuery(allLoggedQ);
			while (rs.next()) {
				role = Role.values()[rs.getInt(2) - 1];
				status = Status.values()[rs.getInt(3) - 1];
				user = new User(rs.getString(1), status, role);
				onlineUserList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return onlineUserList;
	}

	public Role getRole(String nick) {
		ResultSet resultSet = null;
		Role role = null;
		Formatter formatter = new Formatter();
		try (Statement st = connection.createStatement()) {
			formatter.format(roleQ, nick);
			resultSet = st.executeQuery(formatter.toString());
			while (resultSet.next()) {
				String roleString = resultSet.getString("NAME").trim();
				role = Role.valueOf(roleString);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			formatter.close();
		}
		if (role == null) {
			System.err.print("This user does not exist");
		}
		return role;
	}

	@Override
	public List<User> getAllKicked() {
		return OracleDBHandler.getAllKicked(connection);
	}

	@Override
	public int isValid(String login, String password) {
		ResultSet resultSet = null;
		int id = 0;
		try (Statement st = connection.createStatement()) {
			String query = String.format(isValidQ, login, password);
			resultSet = st.executeQuery(query);
			while (resultSet.next()) {
				id = resultSet.getInt(1);
				System.out.println(id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void createUser(User user) {
		try(PreparedStatement ps = connection.prepareStatement(newUserQ)){
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getPicturePath());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
