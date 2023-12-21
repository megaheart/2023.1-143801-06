package com.groupsix.user;

import com.groupsix.attendance.SqliteOfficerAttendanceRepository;

public class UserFactory {

	private Class<IUserRepository> repoClass;

	private static UserFactory instance = new UserFactory();

	public IUserRepository createRepository() {
		try {
			return repoClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void registerRepository(Class repoClass) {
		this.repoClass = repoClass;
	}

	public static UserFactory getInstance() {
		return instance;
	}

}
