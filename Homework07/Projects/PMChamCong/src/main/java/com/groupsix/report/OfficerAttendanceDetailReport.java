package com.groupsix.report;

import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.hrsubsystem.Employee;

import java.util.ArrayList;

public class OfficerAttendanceDetailReport {
	public int getTotalSessions() {
		return totalSessions;
	}

	public void setTotalSessions(int totalSessions) {
		this.totalSessions = totalSessions;
	}

	public double getTotalHoursNotWork() {
		return totalHoursNotWork;
	}

	public void setTotalHoursNotWork(double totalHoursNotWork) {
		this.totalHoursNotWork = totalHoursNotWork;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public ArrayList<OfficerAttendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(ArrayList<OfficerAttendance> attendances) {
		this.attendances = attendances;
	}

	public int getMonthCount() {
		return monthCount;
	}

	public void setMonthCount(int monthCount) {
		this.monthCount = monthCount;
	}

	private int totalSessions;

	private double totalHoursNotWork;

    private int month;

    private int year;

	private int monthCount;

    private Employee employee;

    private ArrayList<OfficerAttendance> attendances;

}
