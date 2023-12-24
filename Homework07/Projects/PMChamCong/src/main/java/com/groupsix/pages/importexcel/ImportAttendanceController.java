package com.groupsix.pages.importexcel;

import com.groupsix.importexcel.ImportHistory;
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

    public ImportAttendanceController(ImportAttendancePanel importAttendancePanel){
        this.importAttendancePanel = importAttendancePanel;
        initialize(null, null);
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initImportButton();
    }

    // Khởi tạo nút import
    private void initImportButton()  {
        importAttendancePanel.importButton.setOnAction(event -> {
            // Open import stage
            FXMLLoader fxmlLoader = new FXMLLoader(ChooseFileImportView.class.getResource("choose-file-import.fxml"));
            try {
                Scene importStage = new Scene(fxmlLoader.load(), 500, 300);
                Stage stage = new Stage();
                stage.setTitle("Nhập du liệu");
                stage.setScene(importStage);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    // Khởi tạo bảng
    private void initTable(){
        var importLogTable = this.importAttendancePanel.importLogTable;
        // Lấy dữ liệu từ database
        ObservableList<ImportHistory> importLogHistories = FXCollections.observableArrayList();
        for(int i = 1; i< 100; i++) {
            importLogHistories.add(new ImportHistory(i, "20/10/2020 10:10:10", "admin"));
        }
        // Căn giữa các cột
        importLogTable.getColumns().stream().forEach(column -> {
            column.setStyle( "-fx-alignment: CENTER;");
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
        actionCol.setStyle( "-fx-alignment: CENTER;");
        importLogTable.getColumns().add(actionCol);
        // Đổ dữ liệu vào bảng
        importLogTable.getItems().addAll(importLogHistories);
    }

}
