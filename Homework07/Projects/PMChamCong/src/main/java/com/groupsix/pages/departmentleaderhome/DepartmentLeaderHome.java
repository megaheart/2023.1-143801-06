package com.groupsix.pages.departmentleaderhome;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DepartmentLeaderHome {
    @FXML
    public Label totalHours;
    @FXML
    public Label totalShifts;
    @FXML
    public Label averageHours;
    @FXML
    public Label averageShifts;

    @FXML
    protected void goToOfficerDepartmentAttendanceReportView(ActionEvent event) {
        if(goToOfficerDepartmentAttendanceReportViewHandler != null)
            goToOfficerDepartmentAttendanceReportViewHandler.handle(event);
    }

    private EventHandler<ActionEvent> goToOfficerDepartmentAttendanceReportViewHandler;

    public void setGoToOfficerDepartmentAttendanceReportViewHandler(EventHandler<ActionEvent> handler) {
        goToOfficerDepartmentAttendanceReportViewHandler = handler;
    }
}
