package com.groupsix.report;

import com.groupsix.attendance.OfficerAttendance;

import java.util.ArrayList;

public class OfficerAttendanceDetailReport {
	public int getTotalShifts() {
		return totalShifts;
	}

	public void setTotalShifts(int totalShifts) {
		this.totalShifts = totalShifts;
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

	public int getEmployee() {
		return employee;
	}

	public void setEmployee(int employee) {
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

	private int totalShifts;

	private double totalHoursNotWork;

    private int month;

    private int year;

	private int monthCount;

    private int employee;

    private ArrayList<OfficerAttendance> attendances;

}
