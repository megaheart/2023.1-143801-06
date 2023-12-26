package com.groupsix.pages.layouts.departmentleader;

import com.groupsix.pages.FXRouter;
import com.groupsix.pages.layouts.INavBar;
import com.groupsix.pages.layouts.NavBarBase;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class NavBar extends NavBarBase implements INavBar {
    @FXML
    public void initialize(){
        super.init();
    }
    @FXML
    protected SubScene subScene;

    @FXML
    protected AnchorPane subSceneAnchorPane;

    @FXML
    protected Label employeeNameLabel;

    @FXML
    protected Label employeeCodeLabel;

    @FXML
    protected Label departmentNameLabel;

    @Override
    protected Label getEmployeeNameLabel() {
        return this.employeeNameLabel;
    }

    @Override
    protected Label getEmployeeIdLabel() {
        return this.employeeCodeLabel;
    }

    @Override
    protected Label getDepartmentNameLabel() {
        return this.departmentNameLabel;
    }

    @Override
    protected AnchorPane getSubSceneAnchorPane() {
        return this.subSceneAnchorPane;
    }

    @Override
    protected SubScene getSubScene() {
        return this.subScene;
    }

    @Override
    public void navigateToHome() {
        FXRouter.goTo("departmentleaderhome");
    }

    @FXML
    protected void userLogout() {
        super.logout();
    }

    @FXML
    protected void goToHome() {
        FXRouter.goTo("departmentleaderhome");
    }
    @FXML
    protected void goToOfficerDepartmentAttendanceReportView() throws Exception {
        FXRouter.goTo("employeeattendance");
    }
}
