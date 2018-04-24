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
	private String getUserQ;

	public OracleUserDAO(Connection connection) {
		this.connection = connection;
		this.updateUserQ = ResourceManager.getRegExp("updateUser");
		this.isValidQ = ResourceManager.getRegExp("isValid");
		this.allLoggedQ = ResourceManager.getRegExp("allLogged");
		this.allKickedQ = ResourceManager.getRegExp("allKicked");
		this.roleQ = ResourceManager.getRegExp("role");
		this.newUserQ = ResourceManager.getRegExp("newUser");
		this.getUserQ = ResourceManager.getRegExp("getUser");
	}

	@Override
	public void logIn(User user) {
		try (PreparedStatement ps = connection.prepareStatement(updateUserQ)) {
			ps.setInt(1, Status.LOGIN.ordinal() + 1);
			ps.setString(2, user.getName());
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
	public void kick(String nick) {
		try (PreparedStatement ps = connection.prepareStatement(updateUserQ)) {
			ps.setInt(1, 4);
			ps.setString(2, nick);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unkick(String nick) {
		try (PreparedStatement ps = connection.prepareStatement(updateUserQ)) {
			ps.setInt(1, Status.LOGOUT.ordinal() + 1);
			ps.setString(2, nick);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		try (PreparedStatement ps = connection.prepareStatement(roleQ)) {
			ps.setString(1, nick);
			resultSet = ps.executeQuery();
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
		List<User> kickedUserList = new ArrayList<User>();
		User user;
		Role role;
		Status status;
		try (Statement st = connection.createStatement();) {
			ResultSet rs = st.executeQuery(allKickedQ);
			while (rs.next()) {
				role = Role.values()[rs.getInt(2) - 1];
				status = Status.values()[rs.getInt(3) - 1];
				user = new User(rs.getString(1), status, role);
				kickedUserList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return kickedUserList;
	}

	@Override
	public int isValid(String login, String password) {
		ResultSet resultSet = null;
		int id = 0;
		try (PreparedStatement ps = connection.prepareStatement(isValidQ)) {
			ps.setString(1, login);
			ps.setString(2, password);
			resultSet = ps.executeQuery();
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
		try (PreparedStatement ps = connection.prepareStatement(newUserQ)) {
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getPicturePath());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<User> getUsersByStatus(Status status) {
		List<User> onlineUserList = new ArrayList<User>();
		User user;
		Role role;
		try (PreparedStatement ps = connection.prepareStatement(allLoggedQ)) {
			ps.setInt(1, status.ordinal() + 1);
			ResultSet rs = ps.executeQuery();
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

	@Override
	public User getUser(String nick) {
		User user = null;
		Role role;
		Status status;
		String picture;
		try (PreparedStatement ps = connection.prepareStatement(getUserQ);) {
			ps.setString(1, nick);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("YEEAAAAAH");
				role = Role.values()[rs.getInt(2) - 1];
				status = Status.values()[rs.getInt(3) - 1];
				picture = rs.getString(4);
				user = new User(nick, status, role, picture);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

}
