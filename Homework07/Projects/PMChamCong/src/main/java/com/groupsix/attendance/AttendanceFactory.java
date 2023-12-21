package com.groupsix.attendance;

public class AttendanceFactory {

	private Class<IOfficerAttendanceRepository> repoClass = null;

	private static AttendanceFactory instance = new AttendanceFactory();

	public IOfficerAttendanceRepository createRepository() {
		try {
			return repoClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void registerRepository(Class<IOfficerAttendanceRepository> repoClass) {
		this.repoClass = repoClass;
	}

	public static AttendanceFactory getInstance() {
		return instance;
	}

}
