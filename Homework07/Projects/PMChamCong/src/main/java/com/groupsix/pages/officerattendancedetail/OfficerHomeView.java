package com.groupsix.pages.officerattendancedetail;

import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.pages.importexcel.ImportLogHistory;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.Date;

public class OfficerHomeView {
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
    public TableView<OfficerAttendance> tableLog;

//    public TableColumn<OfficerAttendance, Date> dateCol;
//    public TableColumn<OfficerAttendance, String> morningCol;
//    public TableColumn<OfficerAttendance, String> afternoonCol;
//
//    public TableColumn<OfficerAttendance, Button> selectionCol;


//    public void insertTable(OfficerAttendance obj){
//        dateCol
//    }

//    public void insertTable(OfficerAttendance obj){
//        tableLog.getItems().add(obj);}
//
//        Callback<TableColumn<ImportLogHistory, String>, TableCell<ImportLogHistory, String>> cellFactory
//                = //
//                new Callback<TableColumn<ImportLogHistory, String>, TableCell<ImportLogHistory, String>>() {
//                    @Override
//                    public TableCell call(final TableColumn<ImportLogHistory, String> param) {
//                        final TableCell<ImportLogHistory, String> cell = new TableCell<ImportLogHistory, String>() {
//
//                            final Button btn = new Button("Chi tiết");
//                            final Button btn2 = new Button("Xóa");
//
//                            @Override
//                            public void updateItem(String item, boolean empty) {
//                                super.updateItem(item, empty);
//                                if (empty) {
//                                    setGraphic(null);
//                                    setText(null);
//                                } else {
//                                    setText(null);
//                                    btn.setOnAction(event -> {
//                                        ImportLogHistory person = getTableView().getItems().get(getIndex());
//                                        System.out.println(person.getId()
//                                                + "   " + person.getTime());
//                                    });
//                                    btn2.setOnAction(event -> {
//                                        ImportLogHistory person = getTableView().getItems().get(getIndex());
//                                        System.out.println(person.getId()
//                                                + "   " + person.getTime());
//                                    });
//                                    btn.setStyle("-fx-background-color: #00ff00; -fx-text-fill: #000;-fx-cursor: hand;");
//                                    btn2.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff;-fx-cursor: hand;");
//                                    // Add two buttons into a cell
//                                    HBox pane = new HBox(btn, btn2);
//                                    pane.alignmentProperty().set(javafx.geometry.Pos.CENTER);
//                                    pane.setSpacing(10);
//                                    setGraphic(pane);
//                                }
//                            }
//                        };
//                        return cell;
//                    }
//                };
//
//        TableColumn<ImportLogHistory, String> actionCol = new TableColumn<>("Action");
//        actionCol.setCellFactory(cellFactory);
//        actionCol.setStyle( "-fx-alignment: CENTER;");
//        importLogTable.getColumns().add(actionCol);
//        importLogTable.getItems().addAll(importLogHistories);
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


    //insertTable(new OfficerAttendance(java.time.LocalDate.now() , true, false));
    }
}
