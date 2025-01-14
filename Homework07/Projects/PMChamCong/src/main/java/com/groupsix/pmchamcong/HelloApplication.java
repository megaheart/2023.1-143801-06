package com.groupsix.pmchamcong;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.attendance.SqliteOfficerAttendanceRepository;
import com.groupsix.base.DatabaseHelper;
import com.groupsix.hrsubsystem.*;
import com.groupsix.importexcel.*;
import com.groupsix.pages.FXRouter;
import com.groupsix.pages.layouts.login.LoginController;
import com.groupsix.pages.layouts.login.LoginPage;
import com.groupsix.request.Request;
import com.groupsix.request.RequestFactory;
import com.groupsix.request.SqliteRequestRepository;
import com.groupsix.user.SqliteUserRepository;
import com.groupsix.user.User;
import com.groupsix.user.UserFactory;
import com.groupsix.user.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "Phần mềm chấm công",1200, 760);
        FXRouter.addLoginPage(LoginPage.class.getResource("LoginPage.fxml"), LoginController.class);
        FXRouter.addNav("of", com.groupsix.pages.layouts.employee.
                NavBar.class.getResource("NavBar.fxml"));
        FXRouter.addNav("dp", com.groupsix.pages.layouts.departmentleader.
                NavBar.class.getResource("NavBar.fxml"));
        FXRouter.addNav("hr", com.groupsix.pages.layouts.hr.
                NavBar.class.getResource("NavBar.fxml"));

        FXRouter.when("employeehome", com.groupsix.pages.employeehome.
                        EmployeeHome.class.getResource("EmployeeHome.fxml"),
                com.groupsix.pages.employeehome.EmployeeHomeController.class, "of");

        FXRouter.when("departmentleaderhome", com.groupsix.pages.departmentleaderhome.
                        DepartmentLeaderHome.class.getResource("DepartmentLeaderHome.fxml"),
                com.groupsix.pages.departmentleaderhome.DepartmentLeaderHomeController.class, "dp");

        FXRouter.when("hrhome", com.groupsix.pages.hrhome.
                        HRHome.class.getResource("HRHome.fxml"),
                com.groupsix.pages.hrhome.HRHomeController.class, "hr");

        FXRouter.when("employeeattendance", com.groupsix.pages.employeeattendance.
                        EmployeeAttendanceController.class.getResource("employee-attendance-view.fxml"),
                com.groupsix.pages.employeeattendance.EmployeeAttendanceController.class, "dp");

        FXRouter.when("officerdepartmentattendancereport", com.groupsix.pages.officerdepartmentattendancereport.
                        OfficerDepartmentAttendanceReportController.class.getResource("officer-department-attendance-report-view.fxml"),
                com.groupsix.pages.officerdepartmentattendancereport.OfficerDepartmentAttendanceReportController.class, "dp");

        FXRouter.when("importview", com.groupsix.pages.importexcel.
                        ImportAttendancePanel.class.getResource("import-view.fxml"),
                com.groupsix.pages.importexcel.ImportAttendanceController.class, "hr");

        FXRouter.when("choose-file-log", com.groupsix.pages.importexcel.
                        ChooseFileImportController.class.getResource("choose-file-import.fxml"),
                com.groupsix.pages.importexcel.ChooseFileImportController.class, "hr");

        FXRouter.when("view-history", com.groupsix.pages.importexcel.
                        AttendanceImportController.class.getResource("history-view.fxml"),
                com.groupsix.pages.importexcel.AttendanceImportController.class, "hr");

        FXRouter.when("officerattendancedetail", com.groupsix.pages.officerattendancedetail.
                        OfficerHomeController.class.getResource("attendance-officer-view-home.fxml"),
                com.groupsix.pages.officerattendancedetail.OfficerHomeController.class, "of");

        FXRouter.when("officerdetailview", com.groupsix.pages.officerattendancedetail.
                        OfficerDetailController.class.getResource("officer-detail-view.fxml"),
                com.groupsix.pages.officerattendancedetail.OfficerDetailController.class, "of");

        FXRouter.when("requesthomeview", com.groupsix.pages.hrchangerequest.
                        RequestHomeController.class.getResource("request-home-view.fxml"),
                com.groupsix.pages.hrchangerequest.RequestHomeController.class, "hr");

        FXRouter.when("requestdetailview", com.groupsix.pages.hrchangerequest.
                        RequestDetailController.class.getResource("request-detail-view.fxml"),
                com.groupsix.pages.hrchangerequest.RequestDetailController.class, "hr");

        FXRouter.showWindow();
    }

    public static void main(String[] args) {
//        launch();
        DatabaseHelper.EnsureTableExist(Employee.class);
        DatabaseHelper.EnsureTableExist(Department.class);
        DatabaseHelper.EnsureTableExist(User.class);
        DatabaseHelper.EnsureTableExist(OfficerAttendance.class);
        DatabaseHelper.EnsureTableExist(ImportHistory.class);
        DatabaseHelper.EnsureTableExist(Request.class);


        HRSubsystemFactory.getInstance().registerEmployeeRepository(EmployeeAdapter.class);
        HRSubsystemFactory.getInstance().registerDepartmentRepository(DepartmentAdapter.class);
        UserFactory.getInstance().registerRepository(SqliteUserRepository.class);
        AttendanceFactory.getInstance().registerRepository(SqliteOfficerAttendanceRepository.class);
        ImportHistoryFactory.getInstance().registerRepository(SqliteHistoryImportRepository.class);
        ImportServiceFactory.getInstance().registerRepository(ImportService.class);
        RequestFactory.getInstance().registerRepository(SqliteRequestRepository.class);


//        UserService.getInstance().authenticate("departmentleader", "1234567");
//        UserService.getInstance().authenticate("officer", "1234567");

        launch();

    }
}