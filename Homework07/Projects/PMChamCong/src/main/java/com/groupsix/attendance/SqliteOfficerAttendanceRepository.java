package com.groupsix.attendance;

import com.groupsix.base.DatabaseHelper;
import com.groupsix.user.User;
import com.groupsix.hrsubsystem.Employee;
import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;

public class SqliteOfficerAttendanceRepository implements IOfficerAttendanceRepository {

	private final Dao<OfficerAttendance, Integer> dao;

	public SqliteOfficerAttendanceRepository() {
		dao = DatabaseHelper.createDAO(OfficerAttendance.class);
	}

	public ArrayList<OfficerAttendance> getAttendancesOfEmployee(User user, Employee employee, int month, int year, int monthCount) {
		try {
			var queryBuilder = dao.queryBuilder();
			var query = queryBuilder.where()
					.eq("employeeCode", employee.getEmployeeCode())
					.and()
					.eq("month", month)
					.and()
					.eq("year", year)
					.prepare();
			return (ArrayList<OfficerAttendance>) dao.query(queryBuilder.prepare());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
