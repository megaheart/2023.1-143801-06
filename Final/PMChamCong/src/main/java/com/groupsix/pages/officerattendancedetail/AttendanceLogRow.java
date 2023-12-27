package com.groupsix.pages.officerattendancedetail;

public class AttendanceLogRow {
    public String date;
    public String morningSession;
    public String afternoonSession;

    public AttendanceLogRow(String date, String morningSession, String afternoonSession) {
        this.date = date;
        this.morningSession = morningSession;
        this.afternoonSession = afternoonSession;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String isMorningSession() {
        return morningSession;
    }

    public void setMorningSession(String morningSession) {
        this.morningSession = morningSession;
    }

    public String isAfternoonSession() {
        return afternoonSession;
    }

    public void setAfternoonSession(String afternoonSession) {
        this.afternoonSession = afternoonSession;
    }
}
