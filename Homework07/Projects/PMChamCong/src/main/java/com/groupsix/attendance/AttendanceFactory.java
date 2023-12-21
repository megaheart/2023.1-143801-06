package com.groupsix.attendance;

public class AttendanceFactory {

	private Class<IOfficerAttendanceRepository> repoClass = null;

	private static AttendanceFactory instance = new AttendanceFactory();

	private AttendanceFactory() {
	}

	public IOfficerAttendanceRepository createRepository() {
		try {
			return repoClass.getDeclaredConstructor().newInstance();
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
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
