package com.groupsix.pages.officerattendancedetail;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class OfficerDetailView {
    @FXML
    public Label dateView;
    @FXML
    public Label morningSession;
    @FXML
    public Label affternoonSession;
    @FXML
    public Label lateView;
    @FXML
    public Label earlyLeaveView;
    @FXML
    public ComboBox<String> morningRequest;
    @FXML
    public ComboBox<String> affternoonRequest;

    @FXML
    public TextField lateRequest;
    @FXML
    public TextField earlyLeaveRequest;

    @FXML
    public TextField reasonRequest;

    @FXML
    public Button closeBtn;
    @FXML
    public Button requestBtn;

    public void initialize() {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Có",
                        "Không"
                );
        morningRequest.setItems(options);
        affternoonRequest.setItems(options);
    }



}
