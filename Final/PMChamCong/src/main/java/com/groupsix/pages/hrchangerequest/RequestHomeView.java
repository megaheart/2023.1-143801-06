package com.groupsix.pages.hrchangerequest;

import com.groupsix.pages.officerattendancedetail.AttendanceLogRow;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RequestHomeView {
    @FXML
    public ComboBox<String> thangComboBox = new ComboBox<>();
    @FXML
    public ComboBox<String> namComboBox = new ComboBox<>();
    @FXML
    public Label waitLabel = new Label();
    @FXML
    public Label acceptedLabel= new Label();
    @FXML
    public Label rejectedLabel = new Label();
    @FXML
    public TextField employeeCodeTextField = new TextField();
    @FXML
    public ComboBox<String> loaiComboBox = new ComboBox<>();
    @FXML
    public Button btnSearch = new Button();
    @FXML
    public TableView<RequestRow> tableRequest = new TableView<>();
    @FXML
    public TableColumn<RequestRow, String> dateCol = new TableColumn<>();
    @FXML
    public TableColumn<RequestRow, String> optionalCol = new TableColumn<>();
    @FXML
    public TableColumn<RequestRow, String> requestIdCol = new TableColumn<>();
    @FXML
    public TableColumn<RequestRow, String> statusCol = new TableColumn<>();
    @FXML
    public TableColumn<RequestRow, String> employeeCodeCol = new TableColumn<>();
    @FXML
    public Label banGhi = new Label();
    @FXML
    public TextField dateTextField = new TextField();
}
