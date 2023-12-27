package com.groupsix.pages.officerdepartmentattendancereport;

import com.groupsix.report.OfficerAttendanceReport;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.time.format.DateTimeFormatter;

public class AttendanceReportExportPanel {

	@FXML
	private TextField pathTextField;

	@FXML
	private ComboBox<String> formatComboBox;

	@FXML
	private void initialize() {
		formatComboBox.getItems().addAll("Excel (.xlsx)", "CSV (.csv)");
		formatComboBox.getSelectionModel().selectFirst();
	}

	@FXML
	private void browseFilePath() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Lưu file báo cáo cho đơn vị");
		var now = java.time.LocalDateTime.now();
		var nowStr = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss").format(now);
		fileChooser.setInitialFileName("Báo cáo chấm công_" + nowStr);

		var fileExtension = getFormat();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel", "*." + fileExtension));

		File selectedFile = fileChooser.showSaveDialog(null);
		if (selectedFile != null) {
			// dialog closed by selecting a file to save the data to
			pathTextField.setText(selectedFile.getAbsolutePath());
		}
	}

	@FXML
	private void clickExportBtn(ActionEvent event) {
		if(exportReportHandler != null)
			exportReportHandler.handle(event);
	}

	private EventHandler<ActionEvent> exportReportHandler;

	public void setExportReportHandler(EventHandler<ActionEvent> exportReportHandler) {
		this.exportReportHandler = exportReportHandler;
	}

	public String getFilePath() {
		return pathTextField.getText();
	}

	public String getFormat() {
		var index = formatComboBox.getSelectionModel().getSelectedIndex();
		if(index == 0) {
			return "xlsx";
		} else {
			return "csv";
		}
	}
}
