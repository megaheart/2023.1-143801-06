package com.groupsix.attendance;

import com.groupsix.hrsubsystem.Employee;
import com.groupsix.user.User;

import java.util.ArrayList;
import java.util.List;

public interface IOfficerAttendanceRepository {

	ArrayList<OfficerAttendance> getAttendancesOfEmployee(User user, Employee employee, int month, int year, int monthCount);

	void insertMany(List<OfficerAttendance> attendances);

	List<OfficerAttendance> getAttendancesByHistoryId(int historyId);

}
