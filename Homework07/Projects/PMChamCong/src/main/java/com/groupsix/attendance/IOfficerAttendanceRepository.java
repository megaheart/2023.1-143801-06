package com.groupsix.attendance;

import com.groupsix.hrsubsystem.Employee;
import com.groupsix.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface IOfficerAttendanceRepository {

	ArrayList<OfficerAttendance> getAttendancesOfEmployee(User user, Employee employee, int month, int year, int monthCount);

	void insertMany(List<OfficerAttendance> attendances);
	OfficerAttendance getChangeLog(Employee e, int id);

	void updateAttendance(boolean morningSession, boolean afternoonSession, double hoursLate, double hoursEarlyLeave, int id);
	OfficerAttendance getChangeLog(Employee e, Date date);
	OfficerAttendance getAttendance(int id);
}
