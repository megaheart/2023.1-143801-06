package com.groupsix.attendance;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDate;
import java.util.Date;

@DatabaseTable(tableName = "OfficerAttendance")
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
	private double hoursEarlyLeave;

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(uniqueCombo = true)
	private String employeeCode;

	@DatabaseField()
	private double hoursLate;

	@DatabaseField(dataType = DataType.DATE_STRING, uniqueCombo = true)
	private Date date;

	@DatabaseField()
	private boolean morningSession;

	@DatabaseField()
	private boolean afternoonSession;

	@DatabaseField
	private Integer historyImportId;

	public Integer getHistoryImportId() {
		return historyImportId;
	}

	public void setHistoryImportId(Integer historyImportId) {
		this.historyImportId = historyImportId;
	}

}
