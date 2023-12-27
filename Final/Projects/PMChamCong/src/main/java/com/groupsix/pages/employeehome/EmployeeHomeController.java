package com.groupsix.pages.employeehome;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.hrsubsystem.Department;
import com.groupsix.hrsubsystem.Employee;
import com.groupsix.hrsubsystem.HRSubsystemFactory;
import com.groupsix.pages.FXRouter;
import com.groupsix.report.OfficerAndAttendance;
import com.groupsix.report.OfficerAttendanceDetailReport;
import com.groupsix.report.OfficerAttendanceReport;
import com.groupsix.report.ReportHelper;
import com.groupsix.user.User;
import com.groupsix.user.UserService;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeHomeController implements Initializable {
    private EmployeeHome view;
    public EmployeeHomeController(EmployeeHome view) {
        this.view = view;
        view.setGoToEmployeeAttendanceViewHandler((e) -> {
            FXRouter.goTo("officerattendancedetail");
        });
        initialize(null, null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();
        User user = UserService.getInstance().getCurrentUser();
        Employee employee = HRSubsystemFactory.getInstance()
                .createEmployeeRepository().getEmployeeByCode(user.getEmployeeCode());
        OfficerAttendanceDetailReport report = getReport(employee, month, year, 1);

        view.totalHours.setText(String.valueOf(report.getTotalHoursNotWork()));
        view.totalShifts.setText(String.valueOf(report.getTotalSessions()));

    }

    public OfficerAttendanceDetailReport getReport(Employee employee, int month, int year, int monthCount) {
        var attendanceRepo = AttendanceFactory.getInstance().createRepository();
        var officerAttendances =
                    attendanceRepo.getAttendancesOfEmployee(
                            UserService.getInstance().getCurrentUser(), employee, month, year, monthCount);
        var report = ReportHelper.summarizeReport(employee, officerAttendances, month, year, monthCount);

        return report;
    }
}
