package com.groupsix.pages.departmentleaderhome;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.attendance.IOfficerAttendanceRepository;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.hrsubsystem.Department;
import com.groupsix.hrsubsystem.Employee;
import com.groupsix.hrsubsystem.HRSubsystemFactory;
import com.groupsix.hrsubsystem.IEmployeeRepository;
import com.groupsix.report.OfficerAttendanceDetailReport;
import com.groupsix.report.ReportHelper;
import com.groupsix.user.User;
import com.groupsix.user.UserService;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentLeaderHomeController implements Initializable {
    private DepartmentLeaderHome view;
    private IOfficerAttendanceRepository officerAttendanceRepository;
    private IEmployeeRepository employeeRepository;
    private ReportHelper reportHelper;
    public DepartmentLeaderHomeController(DepartmentLeaderHome view){
        this.view = view;
        officerAttendanceRepository = AttendanceFactory.getInstance().createRepository();
        employeeRepository = HRSubsystemFactory.getInstance().createEmployeeRepository();
        reportHelper = new ReportHelper();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        User user = UserService.getInstance().getCurrentUser();
        // Get data from database
        Employee employee = employeeRepository.getEmployeeByCode(user.getEmployeeCode());
        Department department = new Department();
        department.setDepartmentCode(employee.getDepartmentCode());

        List<Employee> employees = employeeRepository.getEmployeesInDepartment(department);

//        double totalHours = 0;
//        int totalShifts = 0;
//        double averageHours = 0;
//        int averageShifts = 0;

        //
        view.totalHours.setText("18");
        view.totalShifts.setText("3");
        view.averageHours.setText("6");
        view.averageShifts.setText("1");
    }

}
