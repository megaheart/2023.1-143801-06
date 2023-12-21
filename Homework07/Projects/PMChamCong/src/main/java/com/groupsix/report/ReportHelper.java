package com.groupsix.report;

import com.groupsix.hrsubsystem.Employee;
import com.groupsix.hrsubsystem.Department;

import java.util.ArrayList;

public class ReportHelper {

	public static void writeToExcel(String path, OfficerAttendanceReport report) {

	}

	public static void writeToCsv(String path, OfficerAttendanceReport report) {

	}

	public static OfficerAttendanceDetailReport summarizeReport(Employee employee, ArrayList<OfficerAndAttendance> attendances) {
		return null;
	}

	public static OfficerAttendanceDetailReport summarizeReport(Department department, ArrayList<OfficerAndAttendance> mergedOfficerAttendances) {
		return null;
	}

}
