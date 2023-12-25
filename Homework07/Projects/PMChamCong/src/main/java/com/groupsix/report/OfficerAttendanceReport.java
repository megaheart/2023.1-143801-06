package com.groupsix.report;

import java.util.ArrayList;

public class OfficerAttendanceReport {

	public double getAverageHoursNotWork() {
		return averageHoursNotWork;
	}

	public void setAverageHoursNotWork(double averageHoursNotWork) {
		this.averageHoursNotWork = averageHoursNotWork;
	}

	public int getDepartment() {
		return department;
	}

	public void setDepartment(int department) {
		this.department = department;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public ArrayList<OfficerAndAttendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(ArrayList<OfficerAndAttendance> attendances) {
		this.attendances = attendances;
	}

	public double getAverageSessions() {
		return averageSessions;
	}

	public void setAverageSessions(double averageSessions) {
		this.averageSessions = averageSessions;
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

	public int getTotalSessions() {
		return totalSessions;
	}

	public void setTotalSessions(int totalSessions) {
		this.totalSessions = totalSessions;
	}

	private double averageHoursNotWork;

    private int department;

    private int year;

    private ArrayList<OfficerAndAttendance> attendances;

    private double averageSessions;

    private double totalHoursNotWork;

    private int month;

    private int totalSessions;

}
