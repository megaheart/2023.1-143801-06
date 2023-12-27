package com.groupsix.pages.hrchangerequest;

public class RequestRow {
    public String requestId;
    public String date;
    public String employeeCode;
    public String status;
    public int logAttendanceId;

    public RequestRow(String requestId, String date, String employeeCode, String status, int logAttendanceId) {
        this.requestId = requestId;
        this.date = date;
        this.employeeCode = employeeCode;
        this.status = status;
        this.logAttendanceId = logAttendanceId;
    }

    public RequestRow(String requestId, String date, String employeeCode, int status, int logAttendanceId) {
        this.requestId = requestId;
        this.date = date;
        this.employeeCode = employeeCode;
        if (status == 0) {
            this.status = "Từ chối";
        } else if (status == 1) {
            this.status = "Chờ duyệt";
        } else {
            this.status = "Đã duyệt";
        }
        this.logAttendanceId = logAttendanceId;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
    public String getStatus() {
        return status;
    }
    public void String (String status) {
        this.status = status;
    }
    public void setStatus(int status) {
        if (status == 0) {
            this.status = "Từ chối";
        } else if (status == 1) {
            this.status = "Chờ duyệt";
        } else {
            this.status = "Đã duyệt";
        }
    }
    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    public int getLogAttendanceId() {
        return logAttendanceId;
    }
    public void setLogAttendanceId(int logAttendanceId) {
        this.logAttendanceId = logAttendanceId;
    }

}
