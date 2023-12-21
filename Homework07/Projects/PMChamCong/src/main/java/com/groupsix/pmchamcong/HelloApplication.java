package com.groupsix.pmchamcong;

import com.groupsix.hrsubsystem.EmployeeAdapter;
import com.groupsix.hrsubsystem.HRSubsystemFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        launch();
        HRSubsystemFactory.getInstance().registerEmployeeRepository(EmployeeAdapter.class);

        var factory = HRSubsystemFactory.getInstance();
        var repo = factory.createEmployeeRepository();
        var employees = repo.getEmployees();
    }
}