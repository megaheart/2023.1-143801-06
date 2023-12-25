package com.groupsix.request;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
@DatabaseTable(tableName = "Request")
public class Request {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getHoursEarlyLeave() {
        return hoursEarlyLeave;
    }

    public void setHoursEarlyLeave(double hoursEarlyLeave) {
        this.hoursEarlyLeave = hoursEarlyLeave;
    }

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(uniqueCombo = true)
    private String employeeCode;

    @DatabaseField()
    private double hoursLate;

    @DatabaseField()
    private double hoursEarlyLeave;

    @DatabaseField(dataType = DataType.DATE_STRING, uniqueCombo = true)
    private Date date;

    @DatabaseField()
    private boolean morningSession;

    @DatabaseField()
    private boolean afternoonSession;

    @DatabaseField()
    private String reason;
    @DatabaseField
    private int status;

    
}
