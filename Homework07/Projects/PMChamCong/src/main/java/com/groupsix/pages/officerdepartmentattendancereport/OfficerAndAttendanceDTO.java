package com.groupsix.pages.officerdepartmentattendancereport;

import com.groupsix.report.OfficerAndAttendance;

public class OfficerAndAttendanceDTO {
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotalSession() {
        return totalSession;
    }

    public void setTotalSession(String totalSession) {
        this.totalSession = totalSession;
    }

    public String getTotalHoursNotWork() {
        return totalHoursNotWork;
    }

    public void setTotalHoursNotWork(String totalHoursNotWork) {
        this.totalHoursNotWork = totalHoursNotWork;
    }

    private String fullName;

    private String employeeCode;

    private String departmentName;

    private String month;

    private String totalSession;

    private String totalHoursNotWork;

    private static final java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("#.#");

    public OfficerAndAttendanceDTO(OfficerAndAttendance officerAndAttendance) {
        this.fullName = officerAndAttendance.getFullName();
        this.employeeCode = officerAndAttendance.getEmployeeCode();
        this.departmentName = officerAndAttendance.getDepartmentName();
//        var _month = officerAndAttendance.getMonth();
        this.month = String.valueOf(officerAndAttendance.getMonth());
        this.totalSession = String.valueOf(officerAndAttendance.getTotalSession());
        this.totalHoursNotWork = decimalFormat.format(officerAndAttendance.getHoursNotWork());
    }
}
