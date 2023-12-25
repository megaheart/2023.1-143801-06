package com.groupsix.pages.changerequesthr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class RequestHomeController implements Initializable {

    public RequestHomeView requestHomeView;

    public RequestHomeController(RequestHomeView requestHomeView) {
        this.requestHomeView = requestHomeView;
        initialize(null, null);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Lấy tháng và năm của hệ thống
        int month = java.time.LocalDate.now().getMonthValue();
        int year = java.time.LocalDate.now().getYear();
        this.requestHomeView.thangComboBox.setValue(String.valueOf(month));
        this.requestHomeView.namComboBox.setValue(String.valueOf(year));

        this.requestHomeView.thangComboBox.setItems(FXCollections.observableArrayList(
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
        this.requestHomeView.namComboBox.setItems(yearOptions);

        this.requestHomeView.loaiComboBox.setItems(FXCollections.observableArrayList(
                "Tất cả", "Chờ phê duyệt", "Chấp nhận", "Từ chối"
        ));

    }

}
