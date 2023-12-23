package com.groupsix.attendance;

import com.groupsix.base.DatabaseHelper;
import com.groupsix.user.User;
import com.groupsix.hrsubsystem.Employee;
import com.j256.ormlite.dao.Dao;
import org.apache.poi.ss.usermodel.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqliteOfficerAttendanceRepository implements IOfficerAttendanceRepository {

	private final Dao<OfficerAttendance, Integer> dao;

	public SqliteOfficerAttendanceRepository() {
		dao = DatabaseHelper.createDAO(OfficerAttendance.class);
	}

	public ArrayList<OfficerAttendance> getAttendancesOfEmployee(User user, Employee employee, int month, int year, int monthCount) {
		LocalDate fromDate = LocalDate.of(year, month, 1);
		LocalDate toDate = fromDate.plusMonths(monthCount).minusDays(1);
		var queryBuilder = dao.queryBuilder();
		try {
			queryBuilder.where()
					.eq("employeeCode", employee.getEmployeeCode())
					.and()
					.between("date", fromDate, toDate);
			return (ArrayList<OfficerAttendance>) dao.query(queryBuilder.prepare());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void insertMany(List<OfficerAttendance> attendances) {
		try {
			dao.create(attendances);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
