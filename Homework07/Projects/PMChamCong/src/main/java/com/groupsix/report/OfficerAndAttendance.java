package com.groupsix.report;

public class OfficerAndAttendance {

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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

	public double getHoursNotWork() {
		return hoursNotWork;
	}

	public void setHoursNotWork(double hoursNotWork) {
		this.hoursNotWork = hoursNotWork;
	}

	public int getTotalSession() {
		return totalSession;
	}

	public void setTotalSession(int totalSession) {
		this.totalSession = totalSession;
	}

	private String fullName;

    private String employeeCode;

    private String departmentName;

    private int month;

    private int year;

    private int totalSession;

    private double hoursNotWork;

}
