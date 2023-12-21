package com.groupsix.attendance;

import java.time.LocalDate;

public class OfficerAttendance {

	public double getHoursEarlyLeave() {
		return hoursEarlyLeave;
	}

	public void setHoursEarlyLeave(double hoursEarlyLeave) {
		this.hoursEarlyLeave = hoursEarlyLeave;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public double getHoursLate() {
		return hoursLate;
	}

	public void setHoursLate(double hoursLate) {
		this.hoursLate = hoursLate;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public boolean isMorningSession() {
		return morningSession;
	}

	public void setMorningSession(boolean morningSession) {
		this.morningSession = morningSession;
	}

	public boolean isAfternoonSession() {
		return afternoonSession;
	}

	public void setAfternoonSession(boolean afternoonSession) {
		this.afternoonSession = afternoonSession;
	}

	private double hoursEarlyLeave;

	private String employeeCode;

	private double hoursLate;

	private LocalDate date;

	private boolean morningSession;

	private boolean afternoonSession;

}
