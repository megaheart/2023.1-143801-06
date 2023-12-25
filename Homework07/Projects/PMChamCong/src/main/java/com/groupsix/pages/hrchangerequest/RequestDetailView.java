package com.groupsix.pages.hrchangerequest;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RequestDetailView {
    @FXML
    public Label dateLabel = new Label();
    @FXML
    public Label statusLabel = new Label();
    @FXML
    public Label employeeCodeLabel = new Label();
    @FXML
    public Label oldMorningSessionLabel = new Label();
    @FXML
    public Label oldAfternoonSessionLabel = new Label();
    @FXML
    public Label oldHoursLateLabel = new Label();
    @FXML
    public Label oldHoursEarlyLeaveLabel = new Label();
    @FXML
    public TextArea reasonTextArea = new TextArea();

    @FXML
    public ComboBox<String> morningSessionComboBox = new ComboBox<>();
    @FXML
    public ComboBox<String> afternoonSessionComboBox = new ComboBox<>();
    @FXML
    public TextField hoursLateTextField = new TextField();
    @FXML
    public TextField hoursEarlyLeaveTextField = new TextField();
    @FXML
    TextArea responseTextArea = new TextArea();
    @FXML
    Button closeBtn = new Button();
    @FXML
    Button acceptBtn = new Button();
    @FXML
    Button rejectBtn = new Button();
}
