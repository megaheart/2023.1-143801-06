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

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ChooseFileImportController implements Initializable  {
    public ChooseFileImportView chooseFileImportView;

    public ChooseFileImportController(ChooseFileImportView chooseFileImportView){
        this.chooseFileImportView = chooseFileImportView;
        initialize(null, null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initFileChooser();
        initButtonChooseFile();
    }

    public void initButtonChooseFile() {
        chooseFileImportView.chooseFileButton.setOnAction(event -> {
            System.out.println("Choose file");
            File file = chooseFileImportView.fileChooser.showOpenDialog(
                    chooseFileImportView.chooseFileButton.getScene().getWindow());
            if (file != null) {
                MapFromFileToOfficerAttendance(file);
            }
        });
    }

    public void initFileChooser() {
        chooseFileImportView.fileChooser.setTitle("Chọn file cần import");
        chooseFileImportView.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel", "*.xlsx")
        );
    }


    public List<OfficerAttendance> MapFromFileToOfficerAttendance(File file) {
        try {
            var tableNode = chooseFileImportView.attendanceLogImportTable;
            // Check file is valid
            if(file == null || !file.exists() || !file.getName().endsWith(".xlsx") ) return null;
            System.out.println(file.getAbsolutePath());

            // Read file
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            ObservableList<AttendanceLogImport> attendanceLogImports = FXCollections.observableArrayList();
            // Read each row, skip first row
            sheet.forEach(row -> {
                int index = row.getRowNum();
                if(index == 0) return;
                String time = dataFormatter.formatCellValue(row.getCell(0));
                String code = dataFormatter.formatCellValue(row.getCell(1));
                attendanceLogImports.add(new AttendanceLogImport( index ,time, code));
            });
            TableColumn<AttendanceLogImport,?> indexColumn = tableNode.getColumns().get(0);
            indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
            TableColumn<AttendanceLogImport,?> timeColumn = tableNode.getColumns().get(1);
            timeColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
            TableColumn<AttendanceLogImport,?> codeColumn = tableNode.getColumns().get(2);
            codeColumn.setCellValueFactory(new PropertyValueFactory<>("employeeCode"));

            indexColumn.setStyle( "-fx-alignment: CENTER;");
            timeColumn.setStyle( "-fx-alignment: CENTER;");
            codeColumn.setStyle( "-fx-alignment: CENTER;");

            tableNode.getItems().clear();
            tableNode.getItems().addAll(attendanceLogImports);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
