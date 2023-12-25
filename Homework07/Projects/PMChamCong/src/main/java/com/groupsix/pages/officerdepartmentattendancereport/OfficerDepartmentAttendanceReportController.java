package com.groupsix.pages.officerdepartmentattendancereport;

import com.groupsix.report.OfficerAndAttendance;
import com.groupsix.report.OfficerAttendanceReport;
import com.groupsix.user.User;
import com.groupsix.hrsubsystem.Employee;
import com.groupsix.attendance.OfficerAttendance;

import java.util.ArrayList;

public class OfficerDepartmentAttendanceReportController {

	public OfficerDepartmentAttendanceReportController(OfficerDepartmentAttendanceReportView view) {

	}

	private int department;

	private OfficerAttendanceReport report;

	private User user;

	private ArrayList<Employee> employees;

	private AttendanceReportExportPanel exportPanel;

	public void exportReportAsFile(OfficerAttendanceReport report, String path, String format) {

	}

	public ArrayList<OfficerAndAttendance> mergeOfficerAndAttendance(int officers, ArrayList<OfficerAttendance> officerAttendances) {
		return null;
	}

	public OfficerAttendanceReport getReport(int department, int month, int year, int monthCount) {
		return null;
	}

	public boolean checkPathExist(String path) {
		return false;
	}

	public void showExportPanel() {

	}

	public void closeExportPanel() {

	}

	public void openEmployeeView(Employee employee, int month, int year) {

	}

	public void updateAttendanceList(ArrayList<Employee> officers, OfficerAttendance officerAttendances) {

	}

}
