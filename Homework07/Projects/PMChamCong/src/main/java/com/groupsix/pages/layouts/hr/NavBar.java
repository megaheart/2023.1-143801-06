package com.groupsix.pages.layouts.hr;

import com.groupsix.hrsubsystem.HRSubsystemFactory;
import com.groupsix.pages.FXRouter;
import com.groupsix.pages.layouts.INavBar;
import com.groupsix.pages.layouts.NavBarBase;
import com.groupsix.user.UserService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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
//        FXRouter.goTo("Hi");
    }

    @FXML
    protected void goToHome() {
//        FXRouter.goTo("Hi");
    }
    @FXML
    protected void goToImportAttendancePanel() throws Exception {
//        FXRouter.goTo("Hi");
    }
    @FXML
    protected void goToChangeAttendanceRequestListView() throws Exception {
//        FXRouter.goTo("Hi");
    }
}