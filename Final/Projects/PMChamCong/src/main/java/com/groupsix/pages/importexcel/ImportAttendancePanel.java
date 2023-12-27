package com.groupsix.pages.importexcel;

import com.groupsix.importexcel.ImportHistory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class ImportAttendancePanel  {
    @FXML
    public TableView<ImportHistory> importLogTable;

    @FXML
    public Button importButton;

}
