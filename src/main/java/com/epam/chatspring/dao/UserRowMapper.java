package com.epam.chatspring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.chatspring.model.Role;
import com.epam.chatspring.model.Status;
import com.epam.chatspring.model.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
        user.setId(rs.getInt(1));
        user.setName(rs.getString(2).trim());
        user.setRole(Role.values()[rs.getInt(3)]);
        user.setStatus(Status.values()[rs.getInt(4)]);
        user.setPicturePath(rs.getString(5));
        return user;
	}

}
