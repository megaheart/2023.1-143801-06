package com.groupsix.pmchamcong;

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
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ChooseFileImportController implements Initializable {
    @FXML
    private TableView<AttendanceLogImport> attendanceLogImportTable;

    @FXML
    private Button chooseFileButton;

    @FXML
    private FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initFileChooser();
        initButtonChooseFile();
    }

    private void initButtonChooseFile() {
        chooseFileButton.setOnAction(event -> {
            System.out.println("Choose file");
            File file = fileChooser.showOpenDialog(chooseFileButton.getScene().getWindow());
            if (file != null) {
                System.out.println(file.getAbsolutePath());
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    Workbook workbook = WorkbookFactory.create(fileInputStream);
                    Sheet sheet = workbook.getSheetAt(0);
                    DataFormatter dataFormatter = new DataFormatter();
                    ObservableList<AttendanceLogImport> attendanceLogImports = FXCollections.observableArrayList();
                    sheet.forEach(row -> {
                        int index = row.getRowNum();
                        if(index == 0) return;
                        String time = dataFormatter.formatCellValue(row.getCell(0));
                        String code = dataFormatter.formatCellValue(row.getCell(1));
                        attendanceLogImports.add(new AttendanceLogImport( index ,time, code));
                    });
                    TableColumn<AttendanceLogImport,?> indexColumn = attendanceLogImportTable.getColumns().get(0);
                    indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
                    TableColumn<AttendanceLogImport,?> timeColumn = attendanceLogImportTable.getColumns().get(1);
                    timeColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
                    TableColumn<AttendanceLogImport,?> codeColumn = attendanceLogImportTable.getColumns().get(2);
                    codeColumn.setCellValueFactory(new PropertyValueFactory<>("employeeCode"));

                    indexColumn.setStyle( "-fx-alignment: CENTER;");
                    timeColumn.setStyle( "-fx-alignment: CENTER;");
                    codeColumn.setStyle( "-fx-alignment: CENTER;");

                    attendanceLogImportTable.getItems().clear();
                    attendanceLogImportTable.getItems().addAll(attendanceLogImports);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initFileChooser() {
        fileChooser.setTitle("Chọn file cần import");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel", "*.xlsx")
        );
    }
}
