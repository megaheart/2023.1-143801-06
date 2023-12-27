package com.groupsix.user;

import com.groupsix.attendance.IOfficerAttendanceRepository;
import com.groupsix.base.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;

public class SqliteUserRepository implements IUserRepository {

	private final Dao<User, String> dao;

	public SqliteUserRepository() {
		dao = DatabaseHelper.createDAO(User.class);
	}

	public ArrayList<User> getUsers() {
		try {
			return (ArrayList<User>) dao.queryForAll();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User getUserByUsername(String username) {
		try {
			return dao.queryForId(username);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
