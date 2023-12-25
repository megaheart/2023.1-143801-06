package com.groupsix.pages.officerattendancedetail;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.hrsubsystem.HRSubsystemFactory;
import com.groupsix.pages.FXRouter;
import com.groupsix.user.UserService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OfficerHomeController implements Initializable {
  public OfficerHomeView officerHomeView;
  public OfficerHomeController(OfficerHomeView officerHomeView){
    this.officerHomeView = officerHomeView;
    initialize(null, null);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle){
      initThongKeFunction();
      initButtonThongKe();
  }



  private void initThongKeFunction(){
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
      ArrayList<OfficerAttendance> attendances = getAttendanceDateRow(month, year, 1);
      ArrayList<Double> result = labelThongKet(attendances);
      double hoursLate = result.get(0);
      double hoursEarlyLeave = result.get(1);
      double doubleCountWork = result.get(2);
      int countWork = (int) doubleCountWork;
      initLabelThongKe(countWork, hoursLate, hoursEarlyLeave);
      ArrayList<AttendanceLogRow> rows = new ArrayList<>();
      for (OfficerAttendance attendance : attendances) {
          String dateRow = attendance.getDate().getDate() + " - " + (attendance.getDate().getMonth() + 1) + " - " + (attendance.getDate().getYear() + 1900);
          String morningSession = attendance.isMorningSession() ? "Có" : "Không";
          String afternoonSession = attendance.isAfternoonSession() ? "Có" : "Không";
          AttendanceLogRow row = new AttendanceLogRow(dateRow, morningSession, afternoonSession);
          rows.add(row);
      }
      clearTable();
      addRowTable(rows);
      this.officerHomeView.datePicker.setValue(null);
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

            ArrayList<OfficerAttendance> attendances = getAttendanceDateRow(month, year, 1);
            ArrayList<Double> result = labelThongKet(attendances);
            double hoursLate = result.get(0);
            double hoursEarlyLeave = result.get(1);
            double doubleCountWork = result.get(2);
            int countWork = (int) doubleCountWork;
            initLabelThongKe(countWork, hoursLate, hoursEarlyLeave);
            ArrayList<AttendanceLogRow> rows = new ArrayList<>();
            for (OfficerAttendance attendance : attendances) {
                String dateRow = attendance.getDate().getDate() + " - " + (attendance.getDate().getMonth() + 1) + " - " + (attendance.getDate().getYear() + 1900);
                String morningSession = attendance.isMorningSession() ? "Có" : "Không";
                String afternoonSession = attendance.isAfternoonSession() ? "Có" : "Không";
                AttendanceLogRow row = new AttendanceLogRow(dateRow, morningSession, afternoonSession);
                rows.add(row);
            }
            clearTable();
            addRowTable(rows);
            this.officerHomeView.datePicker.setValue(null);

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
            ArrayList<OfficerAttendance> attendances = getAttendanceDateRow(monthCheck, year, 3);
            ArrayList<Double> result = labelThongKet(attendances);
            double hoursLate = result.get(0);
            double hoursEarlyLeave = result.get(1);
            double doubleCountWork = result.get(2);
            int countWork = (int) doubleCountWork;
            initLabelThongKe(countWork, hoursLate, hoursEarlyLeave);
            ArrayList<AttendanceLogRow> rows = new ArrayList<>();
            for (OfficerAttendance attendance : attendances) {
                String dateRow = attendance.getDate().getDate() + " - " + (attendance.getDate().getMonth() + 1) + " - " + (attendance.getDate().getYear() + 1900);
                String morningSession = attendance.isMorningSession() ? "Có" : "Không";
                String afternoonSession = attendance.isAfternoonSession() ? "Có" : "Không";
                AttendanceLogRow row = new AttendanceLogRow(dateRow, morningSession, afternoonSession);
                rows.add(row);
            }
            clearTable();
            addRowTable(rows);
            this.officerHomeView.datePicker.setValue(null);
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
            ArrayList<OfficerAttendance> attendances = getAttendanceDateRow(1, year, 12);
            ArrayList<Double> result = labelThongKet(attendances);
            double hoursLate = result.get(0);
            double hoursEarlyLeave = result.get(1);
            double doubleCountWork = result.get(2);
            int countWork = (int) doubleCountWork;
            initLabelThongKe(countWork, hoursLate, hoursEarlyLeave);
            ArrayList<AttendanceLogRow> rows = new ArrayList<>();
            for (OfficerAttendance attendance : attendances) {
                String dateRow = attendance.getDate().getDate() + " - " + (attendance.getDate().getMonth() + 1) + " - " + (attendance.getDate().getYear() + 1900);
                String morningSession = attendance.isMorningSession() ? "Có" : "Không";
                String afternoonSession = attendance.isAfternoonSession() ? "Có" : "Không";
                AttendanceLogRow row = new AttendanceLogRow(dateRow, morningSession, afternoonSession);
                rows.add(row);
            }
            clearTable();
            addRowTable(rows);
            this.officerHomeView.datePicker.setValue(null);
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
            Date dateSearch = new Date(year, month - 1, day);
            OfficerAttendance attendance = getAttendanceDateRow(dateSearch);
            if(attendance != null){
                String dateRow = day + " - " + month + " - " + year;
                String morningSession = attendance.isMorningSession() ? "Có" : "Không";
                String afternoonSession = attendance.isAfternoonSession() ? "Có" : "Không";
                AttendanceLogRow row = new AttendanceLogRow(dateRow, morningSession, afternoonSession);
                clearTable();
                ArrayList<AttendanceLogRow> rows = new ArrayList<>();
                rows.add(row);
                addRowTable(rows);
            }else{
                String dateRow = day + " - " + month + " - " + year;
                AttendanceLogRow row = new AttendanceLogRow(dateRow, "Không", "Không");
                clearTable();
                ArrayList<AttendanceLogRow> rows = new ArrayList<>();
                rows.add(row);
                addRowTable(rows);
            }
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
    private void addRowTable(ArrayList<AttendanceLogRow> listAttendanceLog) {
        ObservableList<AttendanceLogRow> attendanceLog = FXCollections.observableArrayList();
        attendanceLog.addAll(listAttendanceLog);
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
                                        var ctrl = (OfficerDetailController) FXRouter.goTo("officerdetailview");
                                        ctrl.setDateLabel(row.getDate());

                                        String[] dateInfo = row.getDate().split(" - ");
                                        int day = Integer.parseInt(dateInfo[0]);
                                        int month = Integer.parseInt(dateInfo[1]);
                                        int year = Integer.parseInt(dateInfo[2]);
                                        Date dateSearch = new Date(year, month - 1, day);
                                        OfficerAttendance attendance = getAttendanceDateRow(dateSearch);
                                        ctrl.settup(attendance);



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
        this.officerHomeView.banGhi.setText(String.valueOf(attendanceLog.size()));
    }
    private void clearTable(){
        this.officerHomeView.tableLog.getItems().clear();
    }


    private OfficerAttendance getAttendanceDateRow(Date date){
        var repo = AttendanceFactory.getInstance().createRepository();
        var user = UserService.getInstance().getCurrentUser();
        System.out.println(user.getEmployeeCode());
        var employee = HRSubsystemFactory.getInstance().createEmployeeRepository().getEmployeeByCode(user.getEmployeeCode());

        int day = date.getDate();
        int month = date.getMonth() + 1;
        int year = date.getYear();
        var attendances = repo.getAttendancesOfEmployee(user, employee, month, year, 1);
        for (OfficerAttendance attendance : attendances) {
            int dayLog = attendance.getDate().getDate();
            if (dayLog == day) {
                return attendance;
            }
        }
        return null;
    }

    private ArrayList<OfficerAttendance> getAttendanceDateRow(int month, int year, int monthCount) {
        var repo = AttendanceFactory.getInstance().createRepository();
        var user = UserService.getInstance().getCurrentUser();
        System.out.println(user.getEmployeeCode());
        var employee = HRSubsystemFactory.getInstance().createEmployeeRepository().getEmployeeByCode(user.getEmployeeCode());

        var attendances = repo.getAttendancesOfEmployee(user, employee, month, year, monthCount);
        return attendances;
    }

    private ArrayList<Double> labelThongKet(ArrayList<OfficerAttendance> attendances){
        double hoursLate = 0;
        double hoursEarlyLeave = 0;
        double countWork = 0;
        for (OfficerAttendance attendance : attendances) {
            if(attendance.isMorningSession()){
                countWork = countWork + 1;
            }
            if(attendance.isAfternoonSession()){
                countWork = countWork + 1;
            }
            hoursLate = hoursLate + attendance.getHoursLate();
            hoursEarlyLeave = hoursEarlyLeave + attendance.getHoursEarlyLeave();

        }
        ArrayList<Double> result = new ArrayList<>();
        result.add(hoursLate);
        result.add(hoursEarlyLeave);
        result.add(countWork);
        return result;
    }

    public void handleDetailBack(int day, int month, int year){
      var dateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
      Date date = new Date(year, month - 1, day);
        OfficerAttendance attendance = getAttendanceDateRow(date);
        initThongKeFunction();
        if(attendance != null){
            String dateRow = dateFormat.format(date);
            String morningSession = attendance.isMorningSession() ? "Có" : "Không";
            String afternoonSession = attendance.isAfternoonSession() ? "Có" : "Không";
            AttendanceLogRow row = new AttendanceLogRow(dateRow, morningSession, afternoonSession);
            clearTable();
            ArrayList<AttendanceLogRow> rows = new ArrayList<>();
            rows.add(row);
            addRowTable(rows);
        }else{
            String dateRow = dateFormat.format(date);
            AttendanceLogRow row = new AttendanceLogRow(dateRow, "Không", "Không");
            clearTable();
            ArrayList<AttendanceLogRow> rows = new ArrayList<>();
            rows.add(row);
            addRowTable(rows);
        }
        LocalDate datePicker = LocalDate.of(year, month, day);
        this.officerHomeView.datePicker.setValue(datePicker);


    }


}
