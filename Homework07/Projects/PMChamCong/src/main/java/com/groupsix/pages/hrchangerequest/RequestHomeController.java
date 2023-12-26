package com.groupsix.pages.hrchangerequest;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.hrsubsystem.HRSubsystemFactory;
import com.groupsix.pages.FXRouter;
import com.groupsix.pages.officerattendancedetail.AttendanceLogRow;
import com.groupsix.request.Request;
import com.groupsix.request.RequestFactory;
import com.groupsix.user.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RequestHomeController implements Initializable {

    public RequestHomeView requestHomeView;

    public RequestHomeController(RequestHomeView requestHomeView) {
        this.requestHomeView = requestHomeView;
        initialize(null, null);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Get current month and year
        int month = java.time.LocalDate.now().getMonthValue();
        int year = java.time.LocalDate.now().getYear();
        this.requestHomeView.thangComboBox.setValue(String.valueOf(month));
        this.requestHomeView.namComboBox.setValue(String.valueOf(year));

        // Initialize buttons
        this.requestHomeView.thangComboBox.setItems(FXCollections.observableArrayList(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11","12"
        ));
        //yearComboBox 2020-2050
        int startYear = 2020;
        int endYear = 2050;
        ObservableList<String> yearOptions =
                FXCollections.observableArrayList();
        for(int i=startYear;i<=endYear;i++){
            yearOptions.add(String.valueOf(i));
        }
        this.requestHomeView.namComboBox.setItems(yearOptions);
        this.requestHomeView.loaiComboBox.setItems(FXCollections.observableArrayList(
                "Tất cả", "Chờ phê duyệt", "Chấp nhận", "Từ chối"
        ));

        ArrayList<Request> requests = getRequests(0, month, year, "", 3);
        ArrayList<RequestRow> requestRows = new ArrayList<>();
        ArrayList<Integer> status_stats = calculateStatus(requests);
        int rejected_count = status_stats.get(0);
        int pending_count = status_stats.get(1);
        int accepted_count = status_stats.get(2);
        initSummarizeLabel(rejected_count, pending_count, accepted_count);
        var dateformat = new SimpleDateFormat("dd/MM/yyyy");
        for (Request request : requests) {
            String dateRow = dateformat.format(request.getDate());
            String requestId = String.valueOf(request.getId());
            String employeeCode = request.getEmployeeCode();
            int status = request.getStatus();
            int logAttendanceId = request.getLogAttendanceId();
            RequestRow row = new RequestRow(requestId, dateRow, employeeCode, status, logAttendanceId);
            requestRows.add(row);
        }

        clearTable();
        addRowTable(requestRows);

        this.requestHomeView.dateTextField.setText("");
        this.requestHomeView.employeeCodeTextField.setText("");
        this.requestHomeView.loaiComboBox.setValue("Tất cả");


        // Set up action for buttons
        this.requestHomeView.btnSearch.setOnAction(event -> {
            int monthSearch = Integer.parseInt(this.requestHomeView.thangComboBox.getValue());
            int yearSearch = Integer.parseInt(this.requestHomeView.namComboBox.getValue());
            String employeeCodeSearch = this.requestHomeView.employeeCodeTextField.getText();
            int statusSearch = 0;
            String statusSearchText = this.requestHomeView.loaiComboBox.getValue();
            if (statusSearchText.equals("Tất cả")) {
                statusSearch = 3;
            } else if (statusSearchText.equals("Chờ phê duyệt")) {
                statusSearch = 1;
            } else if (statusSearchText.equals("Chấp nhận")) {
                statusSearch = 2;
            } else {
                statusSearch = 0;
            }
            int dateSearch = 0;
            String dateSearchText = this.requestHomeView.dateTextField.getText();
            if (dateSearchText == null || dateSearchText.equals("")) {
                dateSearch = 0;
                ArrayList<Request> _requests = getRequests(dateSearch, monthSearch, yearSearch, employeeCodeSearch, statusSearch);
                ArrayList<RequestRow> _requestRows = new ArrayList<>();
                ArrayList<Integer> _status_stats = calculateStatus(_requests);
                int _rejected_count = _status_stats.get(0);
                int _pending_count = _status_stats.get(1);
                int _accepted_count = _status_stats.get(2);
                initSummarizeLabel(_rejected_count, _pending_count, _accepted_count);
                var _dateformat = new SimpleDateFormat("dd/MM/yyyy");
                for (Request request : _requests) {
                    String dateRow = _dateformat.format(request.getDate());
                    String requestId = String.valueOf(request.getId());
                    String employeeCode = request.getEmployeeCode();
                    int status = request.getStatus();
                    int logAttendanceId = request.getLogAttendanceId();
                    RequestRow row = new RequestRow(requestId, dateRow, employeeCode, status, logAttendanceId);
                    _requestRows.add(row);
                }

                clearTable();
                addRowTable(_requestRows);
            } else {

                try {
                    dateSearch = Integer.parseInt(dateSearchText);
                    ArrayList<Request> _requests = getRequests(dateSearch, monthSearch, yearSearch, employeeCodeSearch, statusSearch);
                    ArrayList<RequestRow> _requestRows = new ArrayList<>();
                    ArrayList<Integer> _status_stats = calculateStatus(_requests);
                    int _rejected_count = _status_stats.get(0);
                    int _pending_count = _status_stats.get(1);
                    int _accepted_count = _status_stats.get(2);
                    initSummarizeLabel(_rejected_count, _pending_count, _accepted_count);
                    var _dateformat = new SimpleDateFormat("dd/MM/yyyy");
                    for (Request request : _requests) {
                        String dateRow = _dateformat.format(request.getDate());
                        String requestId = String.valueOf(request.getId());
                        String employeeCode = request.getEmployeeCode();
                        int status = request.getStatus();
                        int logAttendanceId = request.getLogAttendanceId();
                        RequestRow row = new RequestRow(requestId, dateRow, employeeCode, status, logAttendanceId);
                        _requestRows.add(row);
                    }

                    clearTable();
                    addRowTable(_requestRows);
                }
                catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText("Yêu cầu nhập đúng định dạng ngày");
                    alert.setContentText("Vui lòng nhập một số nguyên dương 01-31 hoặc không nhập gì để tìm kiếm tất cả");
                    alert.showAndWait();
                }
            }
        });
    }

    private void clearTable(){
        this.requestHomeView.tableRequest.getItems().clear();
    }

    private ArrayList<Request> getRequests(int date, int month, int year, String employee_code, int status){
        var repo = RequestFactory.getInstance().createRepository();
        var user = UserService.getInstance().getCurrentUser();
        var requests = repo.getRequestOfEmployee(user, date, month, year, employee_code, status);
        return requests;
    }

    private void addRowTable(ArrayList<RequestRow> listRequestRow){
        ObservableList<RequestRow> requestRow = FXCollections.observableArrayList();
        requestRow.addAll(listRequestRow);
        this.requestHomeView.dateCol.setCellValueFactory(new PropertyValueFactory("date"));
        this.requestHomeView.employeeCodeCol.setCellValueFactory(new PropertyValueFactory("employeeCode"));
        this.requestHomeView.statusCol.setCellValueFactory(new PropertyValueFactory("status"));
        this.requestHomeView.requestIdCol.setCellValueFactory(new PropertyValueFactory("requestId"));

        Callback<TableColumn<RequestRow, String>, TableCell<RequestRow, String>> cellFactory
                = //
                new Callback<TableColumn<RequestRow, String>, TableCell<RequestRow, String>>() {
                    @Override
                    public TableCell call(final TableColumn<RequestRow, String> param) {
                        final TableCell<RequestRow, String> cell = new TableCell<RequestRow, String>() {

                            final Button btn = new Button("Xem chi tiết");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);

                                } else {
                                    setText(null);
                                    btn.setOnAction(event -> {
                                        RequestRow row = getTableView().getItems().get(getIndex());
                                        var ctrl = (RequestDetailController) FXRouter.goTo("requestdetailview");
                                        ctrl.setup(row);

                                    });
                                    btn.setStyle("-fx-background-color: #00ff00; -fx-text-fill: #000;-fx-cursor: hand;");
                                    HBox pane = new HBox(btn);
                                    pane.alignmentProperty().set(javafx.geometry.Pos.CENTER);
                                    setGraphic(pane);
                                }
                            }
                        };
                        return cell;
                    }
                };
        this.requestHomeView.optionalCol.setCellFactory(cellFactory);
        this.requestHomeView.optionalCol.setStyle("-fx-alignment: CENTER;");
        this.requestHomeView.tableRequest.getItems().addAll(requestRow);
        this.requestHomeView.banGhi.setText(String.valueOf(requestRow.size()));
    }

    private ArrayList<Integer> calculateStatus(ArrayList<Request> requests){
        ArrayList<Integer> result = new ArrayList<>();
        int pending_count = 0;
        int accepted_count = 0;
        int rejected_count = 0;
        for (Request request : requests) {
            int status = request.getStatus();
            if (status == 0) {
                rejected_count++;
            } else if (status == 1) {
                pending_count++;
            } else if (status == 2){
                accepted_count++;
            }
        }
        result.add(rejected_count);
        result.add(pending_count);
        result.add(accepted_count);
        return result;
    }

    private void initSummarizeLabel(int a, int b, int c){
        this.requestHomeView.rejectedLabel.setText(String.valueOf(a));
        this.requestHomeView.waitLabel.setText(String.valueOf(b));
        this.requestHomeView.acceptedLabel.setText(String.valueOf(c));
    }

}
