package com.groupsix.attendance;

import com.groupsix.base.DatabaseHelper;
import com.groupsix.user.User;
import com.groupsix.hrsubsystem.Employee;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.SelectArg;
import org.apache.poi.ss.usermodel.DateUtil;

import java.time.LocalDate;
import java.time.ZoneId;
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

		Date _fromDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date _toDate = Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		var queryBuilder = dao.queryBuilder();
		try {
			SelectArg fromDateArg = new SelectArg(_fromDate);
			SelectArg toDateArg = new SelectArg(_toDate);
			queryBuilder.where()
					.eq("employeeCode", employee.getEmployeeCode())
					.and()
					.between("date", fromDateArg, toDateArg);

			var statement = queryBuilder.prepare();

//			System.out.println(statement.getStatement());

			return (ArrayList<OfficerAttendance>) dao.query(statement);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void insertMany(List<OfficerAttendance> attendances) {
		try {
			String sql = "INSERT INTO OfficerAttendance (employeeCode, date, hoursEarlyLeave, hoursLate, morningSession, afternoonSession) VALUES ";

			List<String> values = new ArrayList<>();
			for (OfficerAttendance officerAttendance : attendances) {
				String s = String.format("('%s', '%s', %f, %f, %d, %d)",
						officerAttendance.getEmployeeCode(),
						officerAttendance.getDate(),
						officerAttendance.getHoursEarlyLeave(),
						officerAttendance.getHoursLate(),
						officerAttendance.isMorningSession() ? 1 : 0,
						officerAttendance.isAfternoonSession() ? 1 : 0);
				values.add(s);
			}

			sql += String.join(",", values);
			System.out.println(sql);
			dao.executeRaw(sql);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void updateAttendance(boolean morningSession, boolean afternoonSession, double hoursLate, double hoursEarlyLeave, int id) {
		try{
			String sql = "UPDATE OfficerAttendance SET morningSession = " + morningSession + ", afternoonSession = "+ afternoonSession +", hoursLate = "+ hoursLate +", hoursEarlyLeave = "+ hoursEarlyLeave +  " WHERE id = " + id;
			System.out.println(sql);
			var r = dao.executeRaw(sql);
			var x = r;
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}
	}

	public OfficerAttendance getChangeLog(Employee e, int id){
		var queryBuilder = dao.queryBuilder();
		try {
			queryBuilder.where()
					.eq("employeeCode", e.getEmployeeCode())
					.and()
					.eq("id", id);

			var statement = queryBuilder.prepare();

			return dao.queryForFirst(statement);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public OfficerAttendance getChangeLog(Employee e, Date date){
		var queryBuilder = dao.queryBuilder();
		SelectArg _date = new SelectArg(date);
		try {
			queryBuilder.where()
					.eq("employeeCode", e.getEmployeeCode())
					.and()
					.eq("date", _date);

			var statement = queryBuilder.prepare();

			return dao.queryForFirst(statement);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public OfficerAttendance getAttendance(int id) {
		var queryBuilder = dao.queryBuilder();
		try {
			queryBuilder.where()
					.eq("id", id);

			var statement = queryBuilder.prepare();

			return dao.queryForFirst(statement);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
