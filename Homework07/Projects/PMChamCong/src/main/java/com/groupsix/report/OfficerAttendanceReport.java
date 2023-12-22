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

	public double getAverageShifts() {
		return averageShifts;
	}

	public void setAverageShifts(double averageShifts) {
		this.averageShifts = averageShifts;
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

	public int getTotalShifts() {
		return totalShifts;
	}

	public void setTotalShifts(int totalShifts) {
		this.totalShifts = totalShifts;
	}

	private double averageHoursNotWork;

    private int department;

    private int year;

    private ArrayList<OfficerAndAttendance> attendances;

    private double averageShifts;

    private double totalHoursNotWork;

    private int month;

    private int totalShifts;

}
