package com.groupsix.report;

import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.hrsubsystem.Employee;
import com.groupsix.hrsubsystem.Department;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class ReportHelper {

	public static void writeToExcel(String path, OfficerAttendanceReport report) {
		ExportHelper<OfficerAndAttendance> exportHelper = new ExportHelper();
		try {
			exportHelper.exportExcel(report.getAttendances(), "Báo cáo", path, OfficerAndAttendance.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeToCsv(String path, OfficerAttendanceReport report) {
		ExportHelper<OfficerAndAttendance> exportHelper = new ExportHelper();
		try {
			exportHelper.exportCsv(report.getAttendances(), "Báo cáo", path, OfficerAndAttendance.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static OfficerAttendanceDetailReport summarizeReport(Employee employee, ArrayList<OfficerAttendance> attendances, int month, int year, int monthCount) {
		OfficerAttendanceDetailReport report = new OfficerAttendanceDetailReport();
		attendances.sort((a, b) -> a.getDate().compareTo(b.getDate()));
		LocalDate date = LocalDate.of(year, month, 1);
		LocalDate endDate = date.plusMonths(monthCount);

		var newAttendanceList = new ArrayList<OfficerAttendance>();

		int attendanceIndex = 0;

		while(date.isBefore(endDate)) {
			var _date = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

			if(attendanceIndex < attendances.size()) {
				var attendance = attendances.get(attendanceIndex);
				if(attendance.getDate().equals(_date)) {
					newAttendanceList.add(attendance);
					attendanceIndex++;
					date = date.plusDays(1);
					continue;
				}
			}

			var newAttendance = new OfficerAttendance();
			newAttendance.setDate(_date);
			newAttendance.setEmployeeCode(employee.getEmployeeCode());
			newAttendance.setMorningSession(false);
			newAttendance.setAfternoonSession(false);
			newAttendance.setHoursEarlyLeave(0);
			newAttendance.setHoursLate(0);
			newAttendanceList.add(newAttendance);

			date = date.plusDays(1);
		}

		var totalSessions = 0;
		var totalHoursNotWork = 0.0;

		for(var attendance : attendances){
			if(attendance.isMorningSession()) {
				totalSessions++;
			}
			if(attendance.isAfternoonSession()) {
				totalSessions++;
			}
			totalHoursNotWork += attendance.getHoursEarlyLeave();
			totalHoursNotWork += attendance.getHoursLate();
		}

		report.setTotalSessions(totalSessions);
		report.setTotalHoursNotWork(totalHoursNotWork);
		report.setAttendances(newAttendanceList);
		report.setEmployee(employee);
		report.setMonth(month);
		report.setYear(year);
		report.setMonthCount(monthCount);

		return report;
	}

	public static OfficerAttendanceReport summarizeReport(Department department, ArrayList<OfficerAndAttendance> mergedOfficerAttendances, int month, int year, int monthCount) {
		OfficerAttendanceReport report = new OfficerAttendanceReport();

		var attendances = new ArrayList<OfficerAndAttendance>(mergedOfficerAttendances);

		attendances.sort((a, b) -> a.getFullName().compareTo(b.getFullName()));

		report.setAttendances(attendances);

		var totalSessions = 0;
		var totalHoursNotWork = 0.0;

		for(var attendance : attendances){
			totalSessions += attendance.getTotalSession();
			totalHoursNotWork += attendance.getHoursNotWork();
		}

		report.setAverageSessions(totalSessions * 1.0 / attendances.size());
		report.setAverageHoursNotWork(totalHoursNotWork / attendances.size());

		report.setTotalSessions(totalSessions);
		report.setTotalHoursNotWork(totalHoursNotWork);

		report.setDepartment(department);
		report.setMonth(month);
		report.setYear(year);
		report.setMonthCount(monthCount);

		return report;
	}

}
