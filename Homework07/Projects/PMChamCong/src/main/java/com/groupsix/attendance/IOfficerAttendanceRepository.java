package com.groupsix.attendance;

import com.groupsix.hrsubsystem.Employee;
import com.groupsix.user.User;

import java.util.ArrayList;

public interface IOfficerAttendanceRepository {

	ArrayList<OfficerAttendance> getAttendancesOfEmployee(User user, Employee employee, int month, int year, int monthCount);

}
