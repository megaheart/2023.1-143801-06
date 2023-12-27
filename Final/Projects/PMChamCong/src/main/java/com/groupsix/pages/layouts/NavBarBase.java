package com.groupsix.pages.layouts;

import com.groupsix.hrsubsystem.HRSubsystemFactory;
import com.groupsix.user.UserService;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public abstract class NavBarBase implements INavBar{
    protected abstract Label getEmployeeNameLabel();
    protected abstract Label getEmployeeIdLabel();
    protected abstract Label getDepartmentNameLabel();
    protected abstract AnchorPane getSubSceneAnchorPane();
    protected abstract SubScene getSubScene();
    public abstract void navigateToHome();
    @Override
    public void loadPage(String routerLabel, Parent scene) {
        var subSceneAnchorPane = getSubSceneAnchorPane();
        subSceneAnchorPane.getChildren().clear();
        subSceneAnchorPane.getChildren().add(scene);
    }

    public void init() {
        var user = UserService.getInstance().getCurrentUser();
        var employee = HRSubsystemFactory.getInstance()
                .createEmployeeRepository().getEmployeeByCode(user.getEmployeeCode());
        var department = HRSubsystemFactory.getInstance()
                .createDepartmentRepository().getDepartmentByCode(employee.getDepartmentCode());

        var employeeNameLabel = getEmployeeNameLabel();
        var employeeCodeLabel = getEmployeeIdLabel();
        var departmentNameLabel = getDepartmentNameLabel();

        if(user != null) {
            employeeNameLabel.setText(employee.getFullName());
            employeeCodeLabel.setText(user.getEmployeeCode());
            departmentNameLabel.setText(department.getDepartmentName());
        }
        else {
            employeeNameLabel.setText("Không tìm thấy tài khoản");
            employeeCodeLabel.setText("");
            departmentNameLabel.setText("");
        }
    }

    protected void logout() {
        UserService.getInstance().logout();
    }


}
