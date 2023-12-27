package com.groupsix.pages.hrhome;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.hrsubsystem.Employee;
import com.groupsix.hrsubsystem.HRSubsystemFactory;
import com.groupsix.report.OfficerAttendanceDetailReport;
import com.groupsix.report.ReportHelper;
import com.groupsix.user.UserService;
import javafx.fxml.Initializable;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class HRHomeController implements Initializable {
    private HRHome view;
    public HRHomeController(HRHome view){
        this.view = view;
        initialize(null, null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();
        int totalHours = 0;
        int totalShifts = 0;
        DecimalFormat decimalFormat = new java.text.DecimalFormat("#.#");
        List<Employee> employees = HRSubsystemFactory.getInstance().createEmployeeRepository().getEmployees();
        for(Employee employee : employees){
            OfficerAttendanceDetailReport report = getReport(employee, month, year, 1);
            totalHours += report.getTotalHoursNotWork();
            totalShifts += report.getTotalSessions();
        }
        view.totalHours.setText(String.valueOf(totalHours));
        view.totalShifts.setText(String.valueOf(totalShifts));
        view.averageHours.setText(decimalFormat.format(totalHours/employees.size()));
        view.averageShifts.setText(decimalFormat.format(totalShifts/employees.size()));
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
