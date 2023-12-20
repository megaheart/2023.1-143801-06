package com.groupsix.pmchamcong;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ImportController implements Initializable {
    @FXML
    private TableView<ImportLogHistory> importLogTable;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<ImportLogHistory> importLogHistories = FXCollections.observableArrayList();
        for(int i = 1; i< 30; i++) {
            importLogHistories.add(new ImportLogHistory("1", "20/10/2020 10:10:10"));
        }
        importLogTable.setItems(importLogHistories);
    }
}
