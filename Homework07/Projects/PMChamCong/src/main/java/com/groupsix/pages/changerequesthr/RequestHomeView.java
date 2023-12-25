package com.groupsix.pages.changerequesthr;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RequestHomeView {
    @FXML
    public ComboBox<String> thangComboBox = new ComboBox<>();
    @FXML
    public ComboBox<String> namComboBox = new ComboBox<>();
    @FXML
    public Button btnThongKe = new Button();
    @FXML
    public Label waitLabel = new Label();
    @FXML
    public Label acceptedLabel= new Label();
    @FXML
    public Label rejectedLabel = new Label();
    @FXML
    public TextField employeeCodeTextField = new TextField();
    @FXML
    public DatePicker datePicker = new DatePicker();
    @FXML
    public ComboBox<String> loaiComboBox = new ComboBox<>();

    @FXML
    public Button btnSearch = new Button();
    @FXML
    public TableView<RequestRow> tableRequest = new TableView<>();
    @FXML
    public Label banGhi = new Label();



}
