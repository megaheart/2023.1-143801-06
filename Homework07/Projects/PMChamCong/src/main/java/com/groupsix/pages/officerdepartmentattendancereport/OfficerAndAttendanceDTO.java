package com.groupsix.pages.officerdepartmentattendancereport;

import com.groupsix.hrsubsystem.Employee;
import com.groupsix.report.OfficerAndAttendance;

public class OfficerAndAttendanceDTO {
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmployeeCode() {
        return employee.getEmployeeCode();
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

    public String getTotalSessions() {
        return totalSessions;
    }

    public void setTotalSessions(String totalSessions) {
        this.totalSessions = totalSessions;
    }

    public String getTotalHoursNotWork() {
        return totalHoursNotWork;
    }

    public void setTotalHoursNotWork(String totalHoursNotWork) {
        this.totalHoursNotWork = totalHoursNotWork;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    private String fullName;


    private String departmentName;

    private String month;

    private String totalSessions;

    private String totalHoursNotWork;

    private Employee employee;

    public OfficerAndAttendanceDTO() {
    }

    private static final java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("#.#");

    public OfficerAndAttendanceDTO(OfficerAndAttendance officerAndAttendance) {
        this.fullName = officerAndAttendance.getFullName();
        this.employee = officerAndAttendance.getEmployee();
        this.departmentName = officerAndAttendance.getDepartmentName();
//        var _month = officerAndAttendance.getMonth();
//        this.month = String.valueOf(officerAndAttendance.getMonth());
        this.totalSessions = String.valueOf(officerAndAttendance.getTotalSession());
        this.totalHoursNotWork = decimalFormat.format(officerAndAttendance.getHoursNotWork());
    }
}
