package com.groupsix.pages.importexcel;

public class AttendanceLogImport {
    private int index;
    private String timestamp;
    private String employeeCode;

    public AttendanceLogImport(int index, String timestamp, String employeeCode) {
        this.timestamp = timestamp;
        this.employeeCode = employeeCode;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

}
