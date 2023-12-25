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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.security.auth.callback.Callback;
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

        ArrayList<Request> requests = getRequests(0, month, year, "");
        ArrayList<RequestRow> requestRows = new ArrayList<>();
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
        //addRowTable(requestRows);

        this.requestHomeView.dateTextField.setText("");
        this.requestHomeView.employeeCodeTextField.setText("");
    }

    private void clearTable(){
        this.requestHomeView.tableRequest.getItems().clear();
    }

    private ArrayList<Request> getRequests(int date, int month, int year, String employee_code){
        var repo = RequestFactory.getInstance().createRepository();
        var user = UserService.getInstance().getCurrentUser();
        var requests = repo.getRequestOfEmployee(user, date, month, year, employee_code);
        return requests;
    }

/*    private void addRowTable(ArrayList<RequestRow> listRequestRow){
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
                                        RequestRow requestRow = getTableView().getItems().get(getIndex());
                                        var ctrl = (RequestDetailController) FXRouter.goTo("requestdetailview");

                                    });
                                }
                            }
                        };
                        return cell;
                    }
                };*/
/*
    }*/

}
