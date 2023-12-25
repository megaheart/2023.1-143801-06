package com.groupsix.pages.importexcel;

import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.importexcel.AttendanceLogImport;
import com.groupsix.importexcel.IImportService;
import com.groupsix.importexcel.ImportServiceFactory;
import com.groupsix.pages.FXRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ChooseFileImportController implements Initializable  {
    public ChooseFileImportView chooseFileImportView;

    private IImportService importService;

    private List<AttendanceLogImport> attendanceLogImports;
    private File file;

    private void goToImportView() {
        FXRouter.goTo("importview");
    }

    public ChooseFileImportController(ChooseFileImportView chooseFileImportView){
        this.chooseFileImportView = chooseFileImportView;
        importService = ImportServiceFactory.getInstance().createService();
        initialize(null, null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initFileChooser();
        initButtonChooseFile();
        initButtonClose();
        initImportButton();
        initClearButton();
    }

    public void initButtonChooseFile() {
        chooseFileImportView.chooseFileButton.setOnAction(event -> {
            System.out.println("Choose file");
            File fileChoose = chooseFileImportView.fileChooser.showOpenDialog(
                    chooseFileImportView.chooseFileButton.getScene().getWindow());

            if (fileChoose != null) {
                try {
                    file = fileChoose;
                    attendanceLogImports = importService.GetAttendanceLogImportFromFile(file);
                    ObservableList<AttendanceLogImport> observableList = FXCollections.observableArrayList(attendanceLogImports);
                    var table = chooseFileImportView.attendanceLogImportTable;
                    table .getColumns().stream().forEach(column -> {
                        column.setStyle("-fx-alignment: CENTER;");
                    });
                    table.getColumns().stream().forEach(column -> {
                        column.setStyle("-fx-alignment: CENTER;");
                    });
                    // Khởi tạo trường id và time cho bảng
                    table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("index"));
                    table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("timestamp"));
                    table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("employeeCode"));

                    table.getItems().addAll(observableList);

                } catch (Exception e) {
                    System.out.println("ERROR: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    public void initFileChooser() {
        chooseFileImportView.fileChooser.setTitle("Chọn file cần import");
        chooseFileImportView.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel", "*.xlsx")
        );
    }

    public void initButtonClose(){
        chooseFileImportView.closeButton.setOnAction(event -> {
            if (attendanceLogImports == null || attendanceLogImports.size() == 0) {
                goToImportView();
                return;
            }
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Trở lại trang xem lịch sử nhập dữ liệu");
            confirmationDialog.setHeaderText("Xác nhận thoát trang hiện tại?");
            confirmationDialog.setContentText("Dữ liệu hiện tại sẽ bị mất. Bạn có chắc chắn muốn thoát?");

            // Tạo các nút xác nhận và hủy
            ButtonType confirmButton = new ButtonType("Xác nhận");
            ButtonType cancelButton = new ButtonType("Hủy");

            confirmationDialog.getButtonTypes().setAll(cancelButton,confirmButton);

            // Hiển thị dialog và chờ người dùng phản hồi
            Optional<ButtonType> result = confirmationDialog.showAndWait();

            // Xử lý phản hồi từ người dùng
            if (result.isPresent() && result.get() == confirmButton) {
                System.out.println("INFO: Người dùng đã xác nhận quay lại trang xem lịch sử nhập dữ liệu từ trang chọn file");
                // Thực hiện hành động khi người dùng xác nhận
                goToImportView();
            } else {
                System.out.println("INFO: Người dùng đã hủy quay lại trang xem lịch sử nhập dữ liệu");
            }
        });
    }

    private void initImportButton(){
        chooseFileImportView.importButton.setOnAction(event -> {
            if(file == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText("Dữ liệu không hợp lệ");
                alert.setContentText("Dữ liệu không hợp lệ. Vui lòng chọn file excel");
                alert.showAndWait();
            }
            else {
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDialog.setTitle("Nhập dữ liệu");
                confirmationDialog.setHeaderText("Xác nhận nhập dữ liệu hiện tại?");

                // Tạo các nút xác nhận và hủy
                ButtonType confirmButton = new ButtonType("Xác nhận");
                ButtonType cancelButton = new ButtonType("Hủy");

                confirmationDialog.getButtonTypes().setAll(cancelButton,confirmButton);

                // Hiển thị dialog và chờ người dùng phản hồi
                Optional<ButtonType> result = confirmationDialog.showAndWait();

                // Xử lý phản hồi từ người dùng
                if (result.isPresent() && result.get() == confirmButton) {
                    System.out.println("INFO: Người dùng đã xác nhận nhập dữ liệu");
                    // Thực hiện hành động khi người dùng xác nhận
                    try {
                        importService.startImport(file);
                        goToImportView();
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("INFO: Người dùng đã hủy nhập dữ liệu");
                }
            }
        });
    }

    private void initClearButton(){
        chooseFileImportView.clearButton.setOnAction(event -> {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Xóa dữ liệu");
            confirmationDialog.setHeaderText("Xác nhận xóa dữ liệu hiện tại?");

            // Tạo các nút xác nhận và hủy
            ButtonType confirmButton = new ButtonType("Xác nhận");
            ButtonType cancelButton = new ButtonType("Hủy");

            confirmationDialog.getButtonTypes().setAll(cancelButton,confirmButton);

            // Hiển thị dialog và chờ người dùng phản hồi
            Optional<ButtonType> result = confirmationDialog.showAndWait();

            // Xử lý phản hồi từ người dùng
            if (result.isPresent() && result.get() == confirmButton) {
                System.out.println("INFO: Người dùng đã xác nhận xóa dữ liệu");
                // Thực hiện hành động khi người dùng xác nhận
                chooseFileImportView.attendanceLogImportTable.getItems().clear();
                file = null;
                attendanceLogImports = null;
            } else {
                System.out.println("INFO: Người dùng đã hủy xóa dữ liệu");
            }
        });
    }

}
