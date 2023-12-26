package com.groupsix.pages.departmentleaderhome;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.attendance.IOfficerAttendanceRepository;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.hrsubsystem.Department;
import com.groupsix.hrsubsystem.Employee;
import com.groupsix.hrsubsystem.HRSubsystemFactory;
import com.groupsix.hrsubsystem.IEmployeeRepository;
import com.groupsix.report.OfficerAndAttendance;
import com.groupsix.report.OfficerAttendanceDetailReport;
import com.groupsix.report.OfficerAttendanceReport;
import com.groupsix.report.ReportHelper;
import com.groupsix.user.User;
import com.groupsix.user.UserService;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentLeaderHomeController implements Initializable {
    private DepartmentLeaderHome view;
    private IOfficerAttendanceRepository officerAttendanceRepository;
    private IEmployeeRepository employeeRepository;
    public DepartmentLeaderHomeController(DepartmentLeaderHome view){
        this.view = view;
        officerAttendanceRepository = AttendanceFactory.getInstance().createRepository();
        employeeRepository = HRSubsystemFactory.getInstance().createEmployeeRepository();
        initialize(null, null);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        var user = UserService.getInstance().getCurrentUser();
        var employeeRepo = HRSubsystemFactory.getInstance().createEmployeeRepository();
        var employeeOfUser = employeeRepo.getEmployeeByCode(user.getEmployeeCode());
        var _department = HRSubsystemFactory.getInstance().createDepartmentRepository()
                .getDepartmentByCode(employeeOfUser.getDepartmentCode());

        OfficerAttendanceReport report = getReport(_department, month, year, 1);
        DecimalFormat decimalFormat = new java.text.DecimalFormat("#.#");
        view.totalHours.setText(String.valueOf(report.getTotalHoursNotWork()));
        view.totalShifts.setText(String.valueOf(report.getTotalSessions()));
        view.averageHours.setText(decimalFormat.format(report.getAverageHoursNotWork()));
        view.averageShifts.setText(decimalFormat.format(report.getAverageSessions()));
    }

    public OfficerAndAttendance mergeOfficerAndAttendance(Employee officer, Department department,
                                                          ArrayList<OfficerAttendance> officerAttendances) {
        OfficerAndAttendance officerAndAttendance = new OfficerAndAttendance();
        officerAndAttendance.setEmployee(officer);
        officerAndAttendance.setFullName(officer.getFullName());
        officerAndAttendance.setDepartmentName(department.getDepartmentName());

        var totalSession = 0;
        var totalHoursNotWork = 0.0;

        for(var attendance : officerAttendances) {
            if(attendance.isMorningSession()) {
                totalSession++;
            }

            if(attendance.isAfternoonSession()) {
                totalSession++;
            }

            totalHoursNotWork += attendance.getHoursEarlyLeave() + attendance.getHoursLate();
        }

        officerAndAttendance.setTotalSession(totalSession);
        officerAndAttendance.setHoursNotWork(totalHoursNotWork);

        return officerAndAttendance;
    }

    public OfficerAttendanceReport getReport(Department department, int month, int year, int monthCount) {
        var employeeRepo = HRSubsystemFactory.getInstance().createEmployeeRepository();
        var departmentRepo = HRSubsystemFactory.getInstance().createDepartmentRepository();
        var attendanceRepo = AttendanceFactory.getInstance().createRepository();

        var officerList = employeeRepo.getEmployeesInDepartment(department);
        var mergedOfficerAttendances = new ArrayList<OfficerAndAttendance>();

        for(var officer : officerList) {
            var officerAttendances =
                    attendanceRepo.getAttendancesOfEmployee(
                            UserService.getInstance().getCurrentUser(), officer, month, year, monthCount);
            var mergedOfficerAttendance = mergeOfficerAndAttendance(officer,department, officerAttendances);
            mergedOfficerAttendances.add(mergedOfficerAttendance);
        }

        var report = ReportHelper.summarizeReport(department, mergedOfficerAttendances, month, year, monthCount);

        return report;
    }

}
