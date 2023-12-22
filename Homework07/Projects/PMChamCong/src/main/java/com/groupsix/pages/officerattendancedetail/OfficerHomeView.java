package com.groupsix.pages.officerattendancedetail;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.Date;

public class OfficerHomeView {
    @FXML
    public AnchorPane root;
    @FXML
    public ComboBox<String> loaiComboBox;
    @FXML
    public ComboBox<String> listComboBox;
    @FXML
    public ComboBox<String> yearComboBox;

    @FXML
    public Button btnThongKe;
    @FXML
    public Label labelCaLamViec;
    @FXML
    public Label labelSoGioDiMuon;
    @FXML
    public Label labelSoGioVeSom;

    @FXML
//    public TableView<OfficerAttendance> tableLog;

//    public TableColumn<OfficerAttendance, Date> dateCol;
//    public TableColumn<OfficerAttendance, String> morningCol;
//    public TableColumn<OfficerAttendance, String> afternoonCol;
//
//    public TableColumn<OfficerAttendance, Button> selectionCol;


//    public void insertTable(OfficerAttendance obj){
//        dateCol
//    }

    public void initialize() {
        loaiComboBox.setValue("Tháng");

        //Lấy tháng và năm của hệ thống
        int month = java.time.LocalDate.now().getMonthValue();
        int year = java.time.LocalDate.now().getYear();
        listComboBox.setValue(String.valueOf(month));
        yearComboBox.setValue(String.valueOf(year));


        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Tháng",
                        "Quý",
                        "Năm"
                );
        loaiComboBox.setItems(options);

        //yearComboBox 2020-2050
        int startYear = 2020;
        int endYear = 2050;
        ObservableList<String> yearOptions =
                FXCollections.observableArrayList();
        for(int i=startYear;i<=endYear;i++){
            yearOptions.add(String.valueOf(i));
        }
        yearComboBox.setItems(yearOptions);

        loaiComboBox.setOnAction(event -> {
            String selected = loaiComboBox.getSelectionModel().getSelectedItem();
            if ("Tháng".equals(selected)) {
                listComboBox.setItems(FXCollections.observableArrayList(
                        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
                ));
            } else if ("Quý".equals(selected)) {
                listComboBox.setValue("");
                listComboBox.setItems(FXCollections.observableArrayList(
                        "I", "II", "III", "IV"
                ));
            }else {
                listComboBox.setValue("");
                listComboBox.setItems(FXCollections.observableArrayList());
            }
        });

        //Xử lý bảng
//        dateCol.setCellValueFactory(cellData -> {
//            OfficerAttendance attendance = cellData.getValue();
//            Date date = attendance.getDate();
//            return new SimpleObjectProperty<>(date);
//        });
//        morningCol.setCellValueFactory(cellData -> {
//            boolean isMorningSession = cellData.getValue().isMorningSession();
//            String result = isMorningSession ? "Có" : "Không";
//            return new SimpleStringProperty(result);
//        });
//        afternoonCol.setCellValueFactory(cellData -> {
//            boolean isAfternoonSession = cellData.getValue().isAfternoonSession();
//            String result = isAfternoonSession ? "Có" : "Không";
//            return new SimpleStringProperty(result);
//        });
//        selectionCol.setCellValueFactory(cellData -> {
//            Button button = new Button("Chi tiết");
//            button.setOnAction(event -> {
//                System.out.print("Chi tiết");
//            });
//            return null;
//        });


    }
}
