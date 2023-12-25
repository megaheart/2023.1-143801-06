package com.groupsix.pages.employeehome;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeHomeController implements Initializable {
    private EmployeeHome view;
    public EmployeeHomeController(EmployeeHome view) {
        this.view = view;
        initialize(null, null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        view.totalHours.setText("100");
        view.totalShifts.setText("10");
    }
}
