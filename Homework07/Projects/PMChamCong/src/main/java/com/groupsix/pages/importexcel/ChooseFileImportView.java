package com.groupsix.pages.importexcel;

import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.importexcel.AttendanceLogImport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseFileImportView {
    @FXML
    public TableView<AttendanceLogImport> attendanceLogImportTable;

    @FXML
    public Button chooseFileButton;

    @FXML
    public FileChooser fileChooser = new FileChooser();

}
