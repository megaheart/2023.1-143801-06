package com.groupsix.pages.employeeattendance;

import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.base.TimeRange;
import com.groupsix.report.OfficerAttendanceDetailReport;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class EmployeeAttendanceView {

	@FXML
	private ComboBox<String> timeTypeComboBox = new ComboBox<>();

	@FXML
	private ComboBox<String> monthsComboBox = new ComboBox<>();
	@FXML
	private ComboBox<String> quarterComboBox = new ComboBox<>();

	@FXML
	private ComboBox<String> yearsComboBox = new ComboBox<>();

	@FXML
	private Label totalShiftsLabel = new Label();

	@FXML
	private Label totalHoursLateAndLeaveEarlyLabel = new Label();

	@FXML
	private Label currentWeekLabel = new Label();

	@FXML
	private TextField weekWantToGoTxtBox = new TextField();

	@FXML
	private TableView<OfficerAttendance> attendanceLogTable = new TableView<>();

	private TimeRange timeRange = new TimeRange(1, 1 , 1);

	@FXML
	private void initialize() {
		timeTypeComboBox.getItems().addAll("Tháng", "Quý", "Năm");
		monthsComboBox.getItems().addAll("Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
				"Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8",
				"Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12");
		quarterComboBox.getItems().addAll("Quý I", "Quý II", "Quý III", "Quý IV");
		yearsComboBox.getItems().addAll("2020", "2021", "2022", "2023", "2024", "2025");
		setTimeRange(timeRange);
	}

	@FXML
	private void onTimeTypeChanged(ActionEvent event) {
		int index = timeTypeComboBox.getSelectionModel().getSelectedIndex();
		int monthCount = 0;
		int month = timeRange.getMonth() - 1;
		int year = timeRange.getYear();
		switch (index) {
			case 0:
				setTimeRange(new TimeRange(month + 1, year, 1));
				break;
			case 1:
				switch (timeRange.getMonthCount()){
					case 1:
					case 3:
						setTimeRange(new TimeRange((month / 3) * 3 + 1, year, 3));
						break;
					case 12:
						setTimeRange(new TimeRange(1, year, 3));
						break;
				}
				break;
			case 2:
				setTimeRange(new TimeRange(1, year, 12));
				break;
		}
		if(onTimeRangeChangedHandler != null) {
			onTimeRangeChangedHandler.handle(event);
		}
	}

	@FXML
	private void onMonthChanged(ActionEvent event) {
		int month = monthsComboBox.getSelectionModel().getSelectedIndex() + 1;
		setTimeRange(new TimeRange(month, timeRange.getYear(), timeRange.getMonthCount()));
		if(onTimeRangeChangedHandler != null) {
			onTimeRangeChangedHandler.handle(event);
		}
	}

	@FXML
	private void onQuarterChanged(ActionEvent event) {
		int quarter = quarterComboBox.getSelectionModel().getSelectedIndex() + 1;
		setTimeRange(new TimeRange((quarter - 1) * 3 + 1, timeRange.getYear(), 3));
		if(onTimeRangeChangedHandler != null) {
			onTimeRangeChangedHandler.handle(event);
		}
	}

	@FXML
	private void onYearChanged(ActionEvent event) {
		int year = Integer.parseInt(yearsComboBox.getSelectionModel().getSelectedItem());
		setTimeRange(new TimeRange(timeRange.getMonth(), year, timeRange.getMonthCount()));
		if(onTimeRangeChangedHandler != null) {
			onTimeRangeChangedHandler.handle(event);
		}
	}

	@FXML
	private void refreshTable(ActionEvent event) {
		if(refreshTableHandler != null) {
			refreshTableHandler.handle(event);
		}
	}

	@FXML
	private void nextWeek(ActionEvent event) {
		if(nextWeekHandler != null) {
			nextWeekHandler.handle(event);
		}
	}

	@FXML
	private void previousWeek(ActionEvent event) {
		if(previousWeekHandler != null) {
			previousWeekHandler.handle(event);
		}
	}

	@FXML
	private void goToWeek(ActionEvent event) {
		if (goToWeekHandler != null) {
			goToWeekHandler.handle(event);
		}
	}

	private EventHandler<ActionEvent> onTimeRangeChangedHandler;
	private EventHandler<ActionEvent> refreshTableHandler;
	private EventHandler<ActionEvent> nextWeekHandler;
	private EventHandler<ActionEvent> previousWeekHandler;
	private EventHandler<ActionEvent> goToWeekHandler;

	public void setOnTimeRangeChangedHandler(EventHandler<ActionEvent> onTimeRangeChangedHandler) {
		this.onTimeRangeChangedHandler = onTimeRangeChangedHandler;
	}

	public void setRefreshTableHandler(EventHandler<ActionEvent> refreshTableHandler) {
		this.refreshTableHandler = refreshTableHandler;
	}

	public void setNextWeekHandler(EventHandler<ActionEvent> nextWeekHandler) {
		this.nextWeekHandler = nextWeekHandler;
	}

	public void setPreviousWeekHandler(EventHandler<ActionEvent> previousWeekHandler) {
		this.previousWeekHandler = previousWeekHandler;
	}

	public void setGoToWeekHandler(EventHandler<ActionEvent> goToWeekHandler) {
		this.goToWeekHandler = goToWeekHandler;
	}

	public TimeRange getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(TimeRange timeRange) {
		if(timeRange == null) {
			throw new IllegalArgumentException("Time range cannot be null");
		}
		switch (timeRange.getMonthCount()) {
			case 1:
				monthsComboBox.setVisible(true);
				quarterComboBox.setVisible(false);
				yearsComboBox.setVisible(true);
				break;
			case 3:
				monthsComboBox.setVisible(false);
				quarterComboBox.setVisible(true);
				yearsComboBox.setVisible(true);
				break;
			case 12:
				monthsComboBox.setVisible(false);
				quarterComboBox.setVisible(false);
				yearsComboBox.setVisible(true);
				break;
		}
		this.timeRange = timeRange;
	}

	private OfficerAttendanceDetailReport report;

	public void show(OfficerAttendanceDetailReport report) {
		if(report == null) {
			throw new IllegalArgumentException("Report cannot be null");
		}
		this.report = report;

		this.totalShiftsLabel.setText(String.valueOf(report.getTotalShifts()));
		this.totalHoursLateAndLeaveEarlyLabel.setText(String.valueOf(report.getTotalHoursNotWork()));

		setTimeRange(new TimeRange(report.getMonth(), report.getYear(), report.getMonthCount()));

		this.attendanceLogTable.getItems().clear();
		this.attendanceLogTable.getItems().addAll(report.getAttendances());

	}

}
