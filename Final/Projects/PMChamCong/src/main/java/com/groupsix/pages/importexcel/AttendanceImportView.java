package com.groupsix.pages.importexcel;

import com.groupsix.attendance.OfficerAttendance;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Date;

public class AttendanceImportView {
    @FXML
    public Button backButton;
    @FXML
    public Label time;
    @FXML
    public Label createdBy;
    @FXML
    public TableColumn<OfficerAttendance, Date> timeColumn;
    @FXML
    public TableColumn<OfficerAttendance, String> codeColumn;
    @FXML
    public TableColumn<OfficerAttendance, Double> hourLate;

    @FXML
    public TableView<OfficerAttendance> attendanceLogImportTable;
}
