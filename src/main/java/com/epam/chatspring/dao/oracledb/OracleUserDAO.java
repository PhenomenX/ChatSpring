package com.epam.chatspring.dao.oracledb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.epam.chatspring.dao.UserDAO;
import com.epam.chatspring.dao.UserRowMapper;
import com.epam.chatspring.model.Role;
import com.epam.chatspring.model.Status;
import com.epam.chatspring.model.User;

public class OracleUserDAO implements UserDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Value("${updateUser}")
	private String updateUserQ;
	@Value("${isValid}")
	private String isLoginQ;
	@Value("${isKicked}")
	private String isKickedQ;
	@Value("${isValid}")
	private String isValidQ;
	@Value("${role}")
	private String roleQ;
	@Value("${allKicked}")
	private String allKickedQ;
	@Value("${newUser}")
	private String newUserQ;
	@Value("${getUser}")
	private String getUserQ;

	public OracleUserDAO() {
		super();
	}

	@Override
	public void kick(String nick) {
		jdbcTemplate.update(updateUserQ, new Object[] { Status.BANNED.ordinal(), nick });
	}

	@Override
	public void unkick(String nick) {
		jdbcTemplate.update(updateUserQ, new Object[] { Status.NORMAL.ordinal(), nick });
	}

	@Override
	public boolean isKicked(User user) {
		boolean isKicked = true;
		String result = jdbcTemplate.queryForObject(isKickedQ, new Object[] { user.getName() }, String.class);
		isKicked = Boolean.valueOf(result);
		return isKicked;
	}

	public Role getRole(String nick) {
		Role role = null;
		String result = jdbcTemplate.queryForObject(roleQ, new Object[] { nick }, String.class);
		role = Role.valueOf(result);
		return role;
	}

	@Override
	public List<User> getAllKicked() {
		List<User> kickedUserList = new ArrayList<User>();
		kickedUserList = jdbcTemplate.query(allKickedQ, new UserRowMapper());
		return kickedUserList;
	}

	@Override
	public boolean isValid(User user) {
		boolean isValid = true;
		try {
			jdbcTemplate.queryForObject(isValidQ, new Object[] { user.getName(), user.getPassword() },
					Integer.class);
		} catch (EmptyResultDataAccessException e) {
			isValid = false;
		}
		return isValid;
	}

	@Override
	public void createUser(User user) {
		jdbcTemplate.update(newUserQ, new Object[] { user.getName(), user.getPassword(), user.getPicturePath() });
	}

	@Override
	public List<User> getUsersByStatus(Status status) {
		List<User> onlineUserList = new ArrayList<User>();
		onlineUserList = jdbcTemplate.query(allKickedQ, new Object[] { status.ordinal() }, new UserRowMapper());
		return onlineUserList;
	}

	@Override
	public User getUser(String nick) {
		User user = new User();
		user = jdbcTemplate.queryForObject(getUserQ, new Object[] { nick }, new UserRowMapper());
		return user;
	}

	@Override
	public boolean isUnique(String nick) {
		boolean isValid = false;
		try {
			jdbcTemplate.queryForObject(getUserQ, new Object[] { nick }, new UserRowMapper());
		} catch (EmptyResultDataAccessException e) {
			isValid = true;
		}
		return isValid;
	}

}
