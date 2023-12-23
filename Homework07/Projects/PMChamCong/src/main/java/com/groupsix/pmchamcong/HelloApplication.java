package com.groupsix.pmchamcong;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.attendance.SqliteOfficerAttendanceRepository;
import com.groupsix.base.DatabaseHelper;
import com.groupsix.hrsubsystem.*;
import com.groupsix.pages.FXRouter;
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 760);
        stage.setTitle("Phần mềm chấm công");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        launch();
        DatabaseHelper.EnsureTableExist(Employee.class);
        DatabaseHelper.EnsureTableExist(Department.class);
        DatabaseHelper.EnsureTableExist(User.class);
        DatabaseHelper.EnsureTableExist(OfficerAttendance.class);

        HRSubsystemFactory.getInstance().registerEmployeeRepository(EmployeeAdapter.class);
        HRSubsystemFactory.getInstance().registerDepartmentRepository(DepartmentAdapter.class);
        UserFactory.getInstance().registerRepository(SqliteUserRepository.class);
        AttendanceFactory.getInstance().registerRepository(SqliteOfficerAttendanceRepository.class);

        UserService.getInstance().authenticate("hr", "1234567");
//        UserService.getInstance().authenticate("departmentleader", "1234567");
//        UserService.getInstance().authenticate("officer", "1234567");

        launch();

        System.exit(0);
    }
}