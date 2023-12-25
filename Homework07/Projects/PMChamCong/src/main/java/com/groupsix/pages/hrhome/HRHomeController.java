package com.groupsix.pages.hrhome;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class HRHomeController implements Initializable {
    private HRHome view;
    public HRHomeController(HRHome view){
        this.view = view;
        initialize(null, null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        view.totalHours.setText("18");
        view.totalShifts.setText("3");
        view.averageHours.setText("6");
        view.averageShifts.setText("1");
    }
}
