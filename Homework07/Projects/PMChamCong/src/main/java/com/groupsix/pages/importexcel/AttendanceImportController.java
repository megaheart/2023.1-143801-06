package com.groupsix.pages.importexcel;

import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.importexcel.IImportService;
import com.groupsix.importexcel.ImportHistory;
import com.groupsix.importexcel.ImportServiceFactory;
import com.groupsix.pages.FXRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AttendanceImportController implements Initializable {
    private AttendanceImportView view;
    private IImportService importService;

    public AttendanceImportController(AttendanceImportView view) {
        this.view = view;
        importService = ImportServiceFactory.getInstance().createService();
        initialize(null, null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        initLabel();
        initButton();
    }

    public void backToImportAttendance(){
        FXRouter.goTo("importview");
    }

    private void initTable(){
        try {
            int historyId = ImportAttendanceController.historyId;
            ObservableList<OfficerAttendance> officerAttendances =
                    FXCollections.observableArrayList(importService.getOfficerAttendancesByHistoryId(historyId));
            view.codeColumn.setCellValueFactory(new PropertyValueFactory<>("employeeCode"));
            view.timeColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            view.hourLate.setCellValueFactory(new PropertyValueFactory<>("hoursLate"));

            // Căn giữa các cột
            view.attendanceLogImportTable.getColumns().stream().forEach(column -> {
                column.setStyle("-fx-alignment: CENTER;");
            });
            view.attendanceLogImportTable.setItems(officerAttendances);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void initLabel(){
        try {
            ImportHistory importHistory = importService.getHistoryImport(ImportAttendanceController.historyId);
            view.time.setText(importHistory.getTime());
            view.createdBy.setText(importHistory.getCreatedBy());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void initButton(){
        view.backButton.setOnAction(actionEvent -> {
            backToImportAttendance();
        });
    }

}
