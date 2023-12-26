package com.groupsix.report;

import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.base.DatabaseHelper;
import com.groupsix.hrsubsystem.Department;
import com.groupsix.hrsubsystem.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
public class ReportHelperTest {

    @Test
    void testSummarizeReportEmployee_correct() {
        Employee employee = new Employee();
        employee.setEmployeeCode("123");
        employee.setFullName("Nguyen Van A");
        employee.setDepartmentCode("DP01");
        employee.setPositionName("Nhân viên văn phòng");
        int month = 1;
        int year = 2022;
        int monthCount = 1;

        ArrayList<OfficerAttendance> attendances = new ArrayList<>();

        for(int i = 1; i <= 31; i++) {
            OfficerAttendance attendance = new OfficerAttendance();
            attendance.setDate(new Date(year, month, i));
            attendance.setEmployeeCode("123");
            attendance.setMorningSession(true);
            attendance.setAfternoonSession(true);
            attendance.setHoursEarlyLeave(i);
            attendance.setHoursLate(i / 2.0);
            attendances.add(attendance);
        }

        for(int i = 1; i <= 31; i++) {
            if(i % 2 == 0) {
                attendances.get(i - 1).setMorningSession(false);
            }
            if(i % 3 == 1) {
                attendances.get(i - 1).setAfternoonSession(false);
            }
        }

        OfficerAttendanceDetailReport report = ReportHelper.summarizeReport(employee, attendances, month, year, monthCount);

        // Add assertions here based on your expected results
        assertEquals(report.getEmployee().getEmployeeCode(), "123");
        assertEquals(report.getEmployee().getFullName(), "Nguyen Van A");
        assertEquals(report.getEmployee().getDepartmentCode(), "DP01");
        assertEquals(report.getEmployee().getPositionName(), "Nhân viên văn phòng");
        assertEquals(report.getAttendances().size(), 31);
        assertEquals(report.getTotalSessions(), 36);
        assertEquals(report.getTotalHoursNotWork(), 744.0);
    }

    @Test
    void testSummarizeReportDepartment_correct() {
        Department department = new Department();
        department.setDepartmentCode("DP01");
        department.setDepartmentName("Phòng nhân sự");

        ArrayList<OfficerAndAttendance> mergedOfficerAttendances = new ArrayList<>();
        int month = 1;
        int year = 2022;
        int monthCount = 1;

        for (int i = 1; i <= 20; i++){
            var officerAttendance = new OfficerAndAttendance();
            var employee = new Employee();
            employee.setEmployeeCode("EMP00" + i);
            employee.setFullName("Nguyen Van " + i);
            employee.setDepartmentCode("DP01");
            employee.setPositionName("Nhân viên văn phòng");
            officerAttendance.setEmployee(employee);
            officerAttendance.setMonth(month);
            officerAttendance.setYear(year);
            officerAttendance.setTotalSession(i);
            officerAttendance.setHoursNotWork(i / 4.0);
            mergedOfficerAttendances.add(officerAttendance);
        }

        OfficerAttendanceReport report = ReportHelper.summarizeReport(department, mergedOfficerAttendances, month, year, monthCount);

        // Add assertions here based on your expected results
        assertEquals(report.getDepartment().getDepartmentCode(), "DP01");
        assertEquals(report.getDepartment().getDepartmentName(), "Phòng nhân sự");
        assertEquals(report.getAttendances().size(), 20);
        assertEquals(report.getTotalSessions(), 210);
        assertEquals(report.getTotalHoursNotWork(), 52.5);
        assertEquals(report.getAverageSessions(), 10.5);
        assertEquals(report.getAverageHoursNotWork(), 2.625);

    }

}
