package com.groupsix.pages.employeehome;

import com.groupsix.pages.FXRouter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EmployeeHome {
    @FXML
    public Label isCheckIn;
    @FXML
    public Label totalHours;
    @FXML
    public Label totalShifts;

    @FXML
    protected void goToEmployeeAttendanceView(ActionEvent event) {
        if(goToEmployeeAttendanceViewHandler != null)
            goToEmployeeAttendanceViewHandler.handle(event);
    }

    private EventHandler<ActionEvent> goToEmployeeAttendanceViewHandler;

    public void setGoToEmployeeAttendanceViewHandler(EventHandler<ActionEvent> handler) {
        goToEmployeeAttendanceViewHandler = handler;
    }
}
