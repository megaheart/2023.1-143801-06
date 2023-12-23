package com.groupsix.pages.officerattendancedetail;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OfficerHomeController implements Initializable {
  public OfficerHomeView officerHomeView;
  public OfficerHomeController(OfficerHomeView officerHomeView){
    this.officerHomeView = officerHomeView;
    initialize(null, null);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle){
      this.officerHomeView.loaiComboBox.setValue("Tháng");

      //Lấy tháng và năm của hệ thống
      int month = java.time.LocalDate.now().getMonthValue();
      int year = java.time.LocalDate.now().getYear();
      this.officerHomeView.listComboBox.setValue(String.valueOf(month));
      this.officerHomeView.yearComboBox.setValue(String.valueOf(year));


      ObservableList<String> options =
              FXCollections.observableArrayList(
                      "Tháng",
                      "Quý",
                      "Năm"
              );
      this.officerHomeView.loaiComboBox.setItems(options);
      this.officerHomeView.listComboBox.setItems(FXCollections.observableArrayList(
              "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
      ));

      //yearComboBox 2020-2050
      int startYear = 2020;
      int endYear = 2050;
      ObservableList<String> yearOptions =
              FXCollections.observableArrayList();
      for(int i=startYear;i<=endYear;i++){
        yearOptions.add(String.valueOf(i));
      }
      this.officerHomeView.yearComboBox.setItems(yearOptions);

      this.officerHomeView.loaiComboBox.setOnAction(event -> {
        String selected = this.officerHomeView.loaiComboBox.getSelectionModel().getSelectedItem();
        if ("Tháng".equals(selected)) {
            this.officerHomeView.listComboBox.setItems(FXCollections.observableArrayList(
                    "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
            ));
            this.officerHomeView.listComboBox.setValue(String.valueOf(month));
            this.officerHomeView.yearComboBox.setValue(String.valueOf(year));

        } else if ("Quý".equals(selected)) {
          this.officerHomeView.listComboBox.setItems(FXCollections.observableArrayList(
                  "I", "II", "III", "IV"
          ));
            int monthCheck = java.time.LocalDate.now().getMonthValue();
            this.officerHomeView.yearComboBox.setValue(String.valueOf(year));
            if(monthCheck <=3) {
                this.officerHomeView.listComboBox.setValue("I");
            }else if(monthCheck <=6){
                this.officerHomeView.listComboBox.setValue("II");
            }else if(monthCheck <=9){
                this.officerHomeView.listComboBox.setValue("III");
            }else{
                this.officerHomeView.listComboBox.setValue(String.valueOf("IV"));
            }
        }else {
            this.officerHomeView.listComboBox.setValue("");
            this.officerHomeView.yearComboBox.setValue(String.valueOf(year));
          this.officerHomeView.listComboBox.setItems(FXCollections.observableArrayList());
        }
      });
      initButtonThongKe();
  }


  private void initButtonThongKe() {
    this.officerHomeView.btnThongKe.setOnAction(event -> {
      String selected = this.officerHomeView.loaiComboBox.getSelectionModel().getSelectedItem();
      if ("Tháng".equals(selected)) {
        int month = Integer.parseInt(this.officerHomeView.listComboBox.getSelectionModel().getSelectedItem());
        int year = Integer.parseInt(this.officerHomeView.yearComboBox.getSelectionModel().getSelectedItem());

        int yearSystem = java.time.LocalDate.now().getYear();
        int monthSystem = java.time.LocalDate.now().getMonthValue();
        if(year > yearSystem || (year == yearSystem && month > monthSystem)){
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kết quả thống kê");
            alert.setHeaderText("Thống kê không thành công!");
            alert.setContentText("Thời điểm thống kê chưa xảy ra!");
            alert.showAndWait();
        }else{
            //Thêm hàm thống kê vào đây
            initLabelThongKe(2,5,0.5);
            System.out.println("Tháng");
        }
      } else if ("Quý".equals(selected)) {
        String quarter = this.officerHomeView.listComboBox.getSelectionModel().getSelectedItem();
        int year = Integer.parseInt(this.officerHomeView.yearComboBox.getSelectionModel().getSelectedItem());

        int monthCheck = 1;
        switch (quarter) {
              case "I" -> monthCheck = 1;
              case "II" -> monthCheck = 4;
              case "III" -> monthCheck = 7;
              case "IV" -> monthCheck = 10;
        }
        int yearSystem = java.time.LocalDate.now().getYear();
        int monthSystem = java.time.LocalDate.now().getMonthValue();
        if(year > yearSystem || (year == yearSystem && monthCheck > monthSystem)){
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kết quả thống kê");
            alert.setHeaderText("Thống kê không thành công!");
            alert.setContentText("Thời điểm thống kê chưa xảy ra!");
            alert.showAndWait();
        }else{
            //Thêm hàm thống kê vào đây
            System.out.println("Quý");
        }
      }else {
        int year = Integer.parseInt(this.officerHomeView.yearComboBox.getSelectionModel().getSelectedItem());
        if(year > java.time.LocalDate.now().getYear()){
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kết quả thống kê");
            alert.setHeaderText("Thống kê không thành công!");
            alert.setContentText("Thời điểm thống kê chưa xảy ra!");
            alert.showAndWait();
        }else{
            //Thêm hàm thống kê vào đây
            System.out.println("Năm");
        }
      }
    });

    //Xu ly su kien cho DatePicker
    this.officerHomeView.datePicker.setOnAction(event -> {
      int day = this.officerHomeView.datePicker.getValue().getDayOfMonth();
        int month = this.officerHomeView.datePicker.getValue().getMonthValue();
        int year = this.officerHomeView.datePicker.getValue().getYear();
        int daySystem = java.time.LocalDate.now().getDayOfMonth();
        int monthSystem = java.time.LocalDate.now().getMonthValue();
        int yearSystem = java.time.LocalDate.now().getYear();
        if(year > yearSystem || (year == yearSystem && month > monthSystem) || (year == yearSystem && month == monthSystem && day > daySystem)){
            clearTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kết quả thống kê");
            alert.setHeaderText("Không có thống kê nào!");
            alert.setContentText("Thời điểm thống kê chưa xảy ra!");
            alert.showAndWait();
        }else{
            //Thêm hàm thống kê vào đây
            clearTable();
            addRowTable();
            System.out.println("Ngày");
        }
    });

  }

  //Xử lý label thống kê
    private void initLabelThongKe(int a, double b, double c) {
        this.officerHomeView.labelCaLamViec.setText(String.valueOf(a));
        this.officerHomeView.labelSoGioDiMuon.setText(String.valueOf(b));
        this.officerHomeView.labelSoGioVeSom.setText(String.valueOf(c));
    }

    //Add row to table
    private void addRowTable() {
        ObservableList<AttendanceLogRow> attendanceLog = FXCollections.observableArrayList();
        for(int i = 1; i< 100; i++) {
            attendanceLog.add(new AttendanceLogRow(String.valueOf(i), "Có", "Có"));
        }
        this.officerHomeView.tableLog.getColumns().stream().forEach(column -> {
            column.setStyle( "-fx-alignment: CENTER;");
        });
        this.officerHomeView.dateCol.setCellValueFactory(new PropertyValueFactory("date"));
        this.officerHomeView.morningCol.setCellValueFactory(new PropertyValueFactory("morningSession"));
        this.officerHomeView.afternoonCol.setCellValueFactory(new PropertyValueFactory("afternoonSession"));

        Callback<TableColumn<AttendanceLogRow, String>, TableCell<AttendanceLogRow, String>> cellFactory
                = //
                new Callback<TableColumn<AttendanceLogRow, String>, TableCell<AttendanceLogRow, String>>() {
                    @Override
                    public TableCell call(final TableColumn<AttendanceLogRow, String> param) {
                        final TableCell<AttendanceLogRow, String> cell = new TableCell<AttendanceLogRow, String>() {

                            final Button detailBtn = new Button("Xem chi tiết");


                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    setText(null);
                                    detailBtn.setOnAction(event -> {
                                        //Open OfficerDetialView
                                        AttendanceLogRow row = getTableView().getItems().get(getIndex());
                                        System.out.println(row.getDate());
                                        FXMLLoader fxmlLoader = new FXMLLoader(OfficerHomeController.class.getResource("officer-detail-view.fxml"));
                                        try {
                                            Scene importStage = new Scene(fxmlLoader.load());
                                            OfficerDetailView officerDetailView = fxmlLoader.getController();
                                            OfficerDetailController conDetailView = new OfficerDetailController(officerDetailView);
                                            conDetailView.setDateLabel(row.getDate());
                                            Stage stage = new Stage();
                                            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
                                            stage.setTitle("Xem chi tiết");
                                            stage.setScene(importStage);
                                            stage.showAndWait();
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                                    detailBtn.setStyle("-fx-background-color: #00ff00; -fx-text-fill: #000;-fx-cursor: hand;");
                                    // Add two buttons into a cell
                                    HBox pane = new HBox(detailBtn);
                                    pane.alignmentProperty().set(javafx.geometry.Pos.CENTER);
                                    //pane.setSpacing(10);
                                    setGraphic(pane);
                                }
                            }
                        };
                        return cell;
                    }
                };

        this.officerHomeView.optionalCol.setCellFactory(cellFactory);
        this.officerHomeView.optionalCol.setStyle( "-fx-alignment: CENTER;");
        this.officerHomeView.tableLog.getItems().addAll(attendanceLog);
    }
    private void clearTable(){
        this.officerHomeView.tableLog.getItems().clear();
    }

}
