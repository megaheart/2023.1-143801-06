package com.groupsix.pages.officerattendancedetail;

import javafx.fxml.FXML;
import javafx.scene.control.*;


public class OfficerHomeView {
    @FXML
    public ComboBox<String> loaiComboBox = new ComboBox<>();
    @FXML
    public ComboBox<String> listComboBox = new ComboBox<>();
    @FXML
    public ComboBox<String> yearComboBox = new ComboBox<>();

    @FXML
    public Button btnThongKe = new Button();
    @FXML
    public Label labelCaLamViec = new Label();
    @FXML
    public Label labelSoGioDiMuon = new Label();
    @FXML
    public Label labelSoGioVeSom = new Label();
    @FXML
    public DatePicker datePicker = new DatePicker();
    @FXML
    public TableView<AttendanceLogRow> tableLog = new TableView<>();

    @FXML
    public TableColumn<AttendanceLogRow, String> dateCol = new TableColumn<>();
    @FXML
    public TableColumn<AttendanceLogRow, String> morningCol = new TableColumn<>();
    @FXML
    public TableColumn<AttendanceLogRow, String> afternoonCol = new TableColumn<>();
    @FXML
    public TableColumn<AttendanceLogRow, String> optionalCol = new TableColumn<>();

    @FXML
    public Label banGhi = new Label();

}
