package com.groupsix.attendance;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDate;
import java.util.Date;

@DatabaseTable(tableName = "OfficerAttendance")
public class OfficerAttendance {


	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public double getHoursLate() {
		return morningHoursLate + afternoonHoursLate;
	}
	public double getHoursEarlyLeave(){
		return morningHoursLate + afternoonHoursLate;
	}


	public double getMorningHoursEarlyLeave() {
		return morningHoursEarlyLeave;
	}

	public double getMorningHoursLate() {
		return morningHoursLate;
	}

	public double getAfternoonHoursEarlyLeave() {
		return afternoonHoursEarlyLeave;
	}

	public void setMorningHoursEarlyLeave(double morningHoursEarlyLeave) {
		this.morningHoursEarlyLeave = morningHoursEarlyLeave;
	}

	public void setMorningHoursLate(double morningHoursLate) {
		this.morningHoursLate = morningHoursLate;
	}

	public void setAfternoonHoursEarlyLeave(double afternoonHoursEarlyLeave) {
		this.afternoonHoursEarlyLeave = afternoonHoursEarlyLeave;
	}

	public void setAfternoonHoursLate(double afternoonHoursLate) {
		this.afternoonHoursLate = afternoonHoursLate;
	}

	public double getAfternoonHoursLate() {
		return afternoonHoursLate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@DatabaseField()
	private double morningHoursEarlyLeave;

	@DatabaseField()
	private double morningHoursLate;

	@DatabaseField()
	private double afternoonHoursEarlyLeave;

	@DatabaseField()
	private double afternoonHoursLate;

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(uniqueCombo = true)
	private String employeeCode;


	@DatabaseField(dataType = DataType.DATE, uniqueCombo = true)
	private Date date;

	@DatabaseField()
	private boolean morningSession;

	@DatabaseField()
	private boolean afternoonSession;

}
