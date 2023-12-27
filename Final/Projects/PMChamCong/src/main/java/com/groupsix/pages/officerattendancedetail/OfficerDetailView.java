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
    public Label dateView = new Label();
    @FXML
    public Label morningSession = new Label();
    @FXML
    public Label afternoonSession = new Label();
    @FXML
    public Label lateView = new Label();
    @FXML
    public Label earlyLeaveView = new Label();
    @FXML
    public ComboBox<String> morningRequest = new ComboBox<>();
    @FXML
    public ComboBox<String> afternoonRequest = new ComboBox<>();

    @FXML
    public TextField lateRequest = new TextField();
    @FXML
    public TextField earlyLeaveRequest = new TextField();

    @FXML
    public TextField reasonRequest = new TextField();

    @FXML
    public Button closeBtn = new Button();
    @FXML
    public Button requestBtn = new Button();




}
