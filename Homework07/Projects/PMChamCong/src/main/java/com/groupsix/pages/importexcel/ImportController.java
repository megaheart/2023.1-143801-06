package com.groupsix.pages.importexcel;

import com.groupsix.pmchamcong.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ImportController implements Initializable {
    @FXML
    private TableView<ImportLogHistory> importLogTable;

    @FXML
    private Button importButton;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initImportButton();
    }

    private void initImportButton()  {
        importButton.setOnAction(event -> {
            // Open import stage
            FXMLLoader fxmlLoader = new FXMLLoader(AttendanceLogImport.class.getResource("choose-file-import.fxml"));
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
    private void initTable(){
        ObservableList<ImportLogHistory> importLogHistories = FXCollections.observableArrayList();
        for(int i = 1; i< 100; i++) {
            importLogHistories.add(new ImportLogHistory("1", "20/10/2020 10:10:10"));
        }
        importLogTable.getColumns().stream().forEach(column -> {
            column.setStyle( "-fx-alignment: CENTER;");
        });
        importLogTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
        importLogTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("time"));

        Callback<TableColumn<ImportLogHistory, String>, TableCell<ImportLogHistory, String>> cellFactory
                = //
                new Callback<TableColumn<ImportLogHistory, String>, TableCell<ImportLogHistory, String>>() {
                    @Override
                    public TableCell call(final TableColumn<ImportLogHistory, String> param) {
                        final TableCell<ImportLogHistory, String> cell = new TableCell<ImportLogHistory, String>() {

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
                                        ImportLogHistory person = getTableView().getItems().get(getIndex());
                                        System.out.println(person.getId()
                                                + "   " + person.getTime());
                                    });
                                    btn2.setOnAction(event -> {
                                        ImportLogHistory person = getTableView().getItems().get(getIndex());
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

        TableColumn<ImportLogHistory, String> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(cellFactory);
        actionCol.setStyle( "-fx-alignment: CENTER;");
        importLogTable.getColumns().add(actionCol);
        importLogTable.getItems().addAll(importLogHistories);
    }

}
