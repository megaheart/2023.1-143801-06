package com.groupsix.attendance;

import com.groupsix.user.User;

import java.util.ArrayList;

public interface IOfficerAttendanceRepository {

	ArrayList<OfficerAttendance> getAttendancesOfEmployee(User user, int employee, int month, int year, int monthCount);

}
