package com.groupsix.pages.employeeattendance;

import com.groupsix.attendance.OfficerAttendance;

public class OfficerAttendanceDTO {
    private String date;
    private String morningSession;
    private String afternoonSession;
    private String hoursLate;
    private String hoursEarlyLeave;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMorningSession() {
        return morningSession;
    }

    public void setMorningSession(String morningSession) {
        this.morningSession = morningSession;
    }

    public String getAfternoonSession() {
        return afternoonSession;
    }

    public void setAfternoonSession(String afternoonSession) {
        this.afternoonSession = afternoonSession;
    }

    public String getHoursLate() {
        return hoursLate;
    }

    public void setHoursLate(String hoursLate) {
        this.hoursLate = hoursLate;
    }

    public String getHoursEarlyLeave() {
        return hoursEarlyLeave;
    }

    public void setHoursEarlyLeave(String hoursEarlyLeave) {
        this.hoursEarlyLeave = hoursEarlyLeave;
    }

    private static final java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
    private static final java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("#.#");
    public OfficerAttendanceDTO(OfficerAttendance attendance) {
        var date = attendance.getDate();
        this.date = dateFormat.format(date);
        this.morningSession = attendance.isMorningSession() ? "Có" : "Không";
        this.afternoonSession = attendance.isAfternoonSession() ? "Có" : "Không";
        this.hoursLate = decimalFormat.format(attendance.getHoursLate());
        this.hoursEarlyLeave = decimalFormat.format(attendance.getHoursEarlyLeave());
    }
}
