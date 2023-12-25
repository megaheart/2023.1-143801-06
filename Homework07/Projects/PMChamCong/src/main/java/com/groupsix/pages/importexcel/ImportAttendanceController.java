package com.groupsix.pages.importexcel;

import com.groupsix.importexcel.IImportService;
import com.groupsix.importexcel.ImportHistory;
import com.groupsix.importexcel.ImportServiceFactory;
import com.groupsix.pages.FXRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ImportAttendanceController implements Initializable {
    public ImportAttendancePanel importAttendancePanel;

    private IImportService importService;

    public ImportAttendanceController(ImportAttendancePanel importAttendancePanel){
        this.importAttendancePanel = importAttendancePanel;
        importService = ImportServiceFactory.getInstance().createService();
        initialize(null, null);
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initImportButton();
    }

    private void goToChooseFileView() {
        FXRouter.goTo("choose-file-log");
    }

    private void goToViewHistoryImport() {
        FXRouter.goTo("history-view");
    }

    // Khởi tạo nút import
    private void initImportButton()  {
        importAttendancePanel.importButton.setOnAction(event -> {
            goToChooseFileView();
        });
    }

    // Khởi tạo bảng
    private void initTable(){
        try {
            var importLogTable = this.importAttendancePanel.importLogTable;
            var listHistory = importService.getAllHistoryImport();
            // Lấy dữ liệu từ database
            ObservableList<ImportHistory> importLogHistories = FXCollections.observableArrayList();
            if(listHistory == null || listHistory.size() == 0) {
                return;
            }
            for (int i = 0; i < listHistory.size(); i++) {
                importLogHistories.add(
                        new ImportHistory(i+1, listHistory.get(i).getTime(), listHistory.get(i).getCreatedBy()
                        ));
            }
            // Căn giữa các cột
            importLogTable.getColumns().stream().forEach(column -> {
                column.setStyle("-fx-alignment: CENTER;");
            });
            // Khởi tạo trường id và time cho bảng
            importLogTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
            importLogTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("time"));
            importLogTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("createdBy"));

            // Tạo cột, tại mỗi dòng sẽ có 2 button là chi tiết và xóa
            Callback<TableColumn<ImportHistory, String>, TableCell<ImportHistory, String>> cellFactory
                    = //
                    new Callback<TableColumn<ImportHistory, String>, TableCell<ImportHistory, String>>() {
                        @Override
                        public TableCell call(final TableColumn<ImportHistory, String> param) {
                            final TableCell<ImportHistory, String> cell = new TableCell<ImportHistory, String>() {

                                final Button btn = new Button("Chi tiết");
                                final Button btn2 = new Button("Xóa");

                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty) {
                                        setGraphic(null);
                                        setText(null);
                                    } else {
                                        setText(null);
                                        btn.setOnAction(event -> {
                                            ImportHistory person = getTableView().getItems().get(getIndex());
                                            System.out.println(person.getId()
                                                    + "   " + person.getTime());
                                        });
                                        btn2.setOnAction(event -> {
                                            ImportHistory person = getTableView().getItems().get(getIndex());
                                            System.out.println(person.getId()
                                                    + "   " + person.getTime());
                                        });
                                        btn.setStyle("-fx-background-color: #00ff00; -fx-text-fill: #000;-fx-cursor: hand;");
                                        btn2.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff;-fx-cursor: hand;");
                                        // Add two buttons into a cell
                                        HBox pane = new HBox(btn, btn2);
                                        pane.alignmentProperty().set(javafx.geometry.Pos.CENTER);
                                        pane.setSpacing(10);
                                        setGraphic(pane);
                                    }
                                }
                            };
                            return cell;
                        }
                    };
            // Tạo cột action, tại mỗi dòng sẽ có 2 button là chi tiết và xóa
            TableColumn<ImportHistory, String> actionCol = new TableColumn<>("Action");
            actionCol.setCellFactory(cellFactory);
            actionCol.setStyle("-fx-alignment: CENTER;");
            importLogTable.getColumns().add(actionCol);
            // Đổ dữ liệu vào bảng
            importLogTable.getItems().addAll(importLogHistories);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
