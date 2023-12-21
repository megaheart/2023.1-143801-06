package com.groupsix.pages.officerattendancedetail;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class OfficerHomeView {
    @FXML
    public ComboBox<String> loaiComboBox;
    @FXML
    public ComboBox<String> listComboBox;
    @FXML
    public ComboBox<String> yearComboBox;

    public void initialize() {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Tháng",
                        "Quý",
                        "Năm"
                );
        loaiComboBox.setItems(options);
    }
}
