package com.groupsix.pages.employeeattendance;

import com.almasb.fxgl.ui.DialogBox;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.base.IEventHandler;
import com.groupsix.base.TimeRange;
import com.groupsix.pages.officerattendancedetail.AttendanceLogRow;
import com.groupsix.report.OfficerAttendanceDetailReport;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
	private Label totalSessionsLabel = new Label();

	@FXML
	private Label totalHoursLateAndLeaveEarlyLabel = new Label();

	@FXML
	private Label currentWeekLabel = new Label();

	@FXML
	private TextField weekWantToGoTxtBox = new TextField();

	@FXML
	private TableView<OfficerAttendanceDTO> attendanceLogTable = new TableView<OfficerAttendanceDTO>();

	@FXML
	public TableColumn<OfficerAttendanceDTO, String> dateCol = new TableColumn<>();
	@FXML
	public TableColumn<OfficerAttendanceDTO, String> morningSessionCol = new TableColumn<>();
	@FXML
	public TableColumn<OfficerAttendanceDTO, String> afternoonSessionCol = new TableColumn<>();
	@FXML
	public TableColumn<OfficerAttendanceDTO, String> hoursLateCol = new TableColumn<>();
	@FXML
	public TableColumn<OfficerAttendanceDTO, String> hoursEarlyLeaveCol = new TableColumn<>();

	@FXML
	private Label employeeInfoLabel = new Label();

	@FXML
	private Button nextWeekBtn = new Button();

	@FXML
	private Button previousWeekBtn = new Button();

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

		dateCol.setCellValueFactory(new PropertyValueFactory("date"));
		morningSessionCol.setCellValueFactory(new PropertyValueFactory("morningSession"));
		afternoonSessionCol.setCellValueFactory(new PropertyValueFactory("afternoonSession"));
		hoursLateCol.setCellValueFactory(new PropertyValueFactory("hoursLate"));
		hoursEarlyLeaveCol.setCellValueFactory(new PropertyValueFactory("hoursEarlyLeave"));
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
		if(report == null) return;
		int currentWeek = Integer.parseInt(currentWeekLabel.getText().split("/")[0]);
		if(currentWeek == weekCount) return;
		currentWeek++;
		currentWeekLabel.setText(currentWeek + "/" + weekCount);

		var fromDate = LocalDate.of(report.getYear(), report.getMonth(), 1);
		fromDate = fromDate.plusDays(7 * (currentWeek - 1));
		var toDate = fromDate.plusDays(7);
		var _fromDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		var _toDate = Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		var attendances = report.getAttendances().stream()
				.filter(attendance -> attendance.getDate().compareTo(_fromDate) >= 0 && attendance.getDate().compareTo(_toDate) < 0)
				.map(attendance -> new OfficerAttendanceDTO(attendance)).toArray(OfficerAttendanceDTO[]::new);

		this.attendanceLogTable.getItems().clear();
		this.attendanceLogTable.getItems().addAll(attendances);

		if(currentWeek == weekCount) {
			nextWeekBtn.setDisable(true);
		}else {
			nextWeekBtn.setDisable(false);
		}

		if(currentWeek == 1) {
			previousWeekBtn.setDisable(true);
		} else {
			previousWeekBtn.setDisable(false);
		}
	}

	@FXML
	private void previousWeek(ActionEvent event) {
		if(report == null) return;

		int currentWeek = Integer.parseInt(currentWeekLabel.getText().split("/")[0]);
		if(currentWeek == 1) return;
		currentWeek--;
		currentWeekLabel.setText(currentWeek + "/" + weekCount);

		var fromDate = LocalDate.of(report.getYear(), report.getMonth(), 1);
		fromDate = fromDate.plusDays(7 * (currentWeek - 1));
		var toDate = fromDate.plusDays(7);
		var _fromDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		var _toDate = Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		var attendances = report.getAttendances().stream()
				.filter(attendance -> attendance.getDate().compareTo(_fromDate) >= 0 && attendance.getDate().compareTo(_toDate) < 0)
				.map(attendance -> new OfficerAttendanceDTO(attendance)).toArray(OfficerAttendanceDTO[]::new);

		this.attendanceLogTable.getItems().clear();
		this.attendanceLogTable.getItems().addAll(attendances);

		if(currentWeek == weekCount) {
			nextWeekBtn.setDisable(true);
		} else {
			nextWeekBtn.setDisable(false);
		}

		if(currentWeek == 1) {
			previousWeekBtn.setDisable(true);
		} else {
			previousWeekBtn.setDisable(false);
		}
	}

	@FXML
	private void goToWeek(ActionEvent event) {
		int week = 0;
		try {
			week = Integer.parseInt(weekWantToGoTxtBox.getText());
		} catch (NumberFormatException e) {
			weekWantToGoTxtBox.setText("");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText("Lỗi nhập liệu");
			alert.setContentText("Vui lòng nhập số tuần hợp lệ, không được bỏ trống.");
			alert.showAndWait();
			weekWantToGoTxtBox.requestFocus();
			return;
		}

		if(week < 1 || week > weekCount){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText("Lỗi nhập liệu");
			alert.setContentText("Vui lòng nhập số tuần trong khoảng từ 1 đến " + weekCount);
			alert.showAndWait();
			weekWantToGoTxtBox.requestFocus();
			return;
		}

		currentWeekLabel.setText(week + "/" + weekCount);

		var fromDate = LocalDate.of(report.getYear(), report.getMonth(), 1);
		fromDate = fromDate.plusDays(7 * (week - 1));
		var toDate = fromDate.plusDays(7);
		var _fromDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		var _toDate = Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		var attendances = report.getAttendances().stream()
				.filter(attendance -> attendance.getDate().compareTo(_fromDate) >= 0 && attendance.getDate().compareTo(_toDate) < 0)
				.map(attendance -> new OfficerAttendanceDTO(attendance)).toArray(OfficerAttendanceDTO[]::new);

		this.attendanceLogTable.getItems().clear();
		this.attendanceLogTable.getItems().addAll(attendances);

		if(week == weekCount) {
			nextWeekBtn.setDisable(true);
		} else {
			nextWeekBtn.setDisable(false);
		}

		if(week == 1) {
			previousWeekBtn.setDisable(true);
		} else {
			previousWeekBtn.setDisable(false);
		}

	}

	private EventHandler<ActionEvent> onTimeRangeChangedHandler;
	private EventHandler<ActionEvent> refreshTableHandler;
	private EventHandler<ActionEvent> nextWeekHandler;
	private EventHandler<ActionEvent> previousWeekHandler;
	private IEventHandler<ActionEvent, Integer> goToWeekHandler;

	public void setOnTimeRangeChangedHandler(EventHandler<ActionEvent> onTimeRangeChangedHandler) {
		this.onTimeRangeChangedHandler = onTimeRangeChangedHandler;
	}

	public void setRefreshTableHandler(EventHandler<ActionEvent> refreshTableHandler) {
		this.refreshTableHandler = refreshTableHandler;
	}

//	public void setNextWeekHandler(EventHandler<ActionEvent> nextWeekHandler) {
//		this.nextWeekHandler = nextWeekHandler;
//	}
//
//	public void setPreviousWeekHandler(EventHandler<ActionEvent> previousWeekHandler) {
//		this.previousWeekHandler = previousWeekHandler;
//	}
//
//	public void setGoToWeekHandler(IEventHandler<ActionEvent, Integer> goToWeekHandler) {
//		this.goToWeekHandler = goToWeekHandler;
//	}

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
				monthsComboBox.setManaged(true);
				quarterComboBox.setManaged(false);
				yearsComboBox.setManaged(true);
				timeTypeComboBox.getSelectionModel().select(0);
				monthsComboBox.getSelectionModel().select(timeRange.getMonth() - 1);
				yearsComboBox.getSelectionModel().select(String.valueOf(timeRange.getYear()));
				break;
			case 3:
				monthsComboBox.setVisible(false);
				quarterComboBox.setVisible(true);
				yearsComboBox.setVisible(true);
				monthsComboBox.setManaged(false);
				quarterComboBox.setManaged(true);
				yearsComboBox.setManaged(true);
				timeTypeComboBox.getSelectionModel().select(1);
				quarterComboBox.getSelectionModel().select((timeRange.getMonth() - 1) / 3);
				yearsComboBox.getSelectionModel().select(String.valueOf(timeRange.getYear()));
				break;
			case 12:
				monthsComboBox.setVisible(false);
				quarterComboBox.setVisible(false);
				yearsComboBox.setVisible(true);
				monthsComboBox.setManaged(false);
				quarterComboBox.setManaged(false);
				yearsComboBox.setManaged(true);
				timeTypeComboBox.getSelectionModel().select(2);
				yearsComboBox.getSelectionModel().select(String.valueOf(timeRange.getYear()));
				break;
		}
		this.timeRange = timeRange;
	}

	private OfficerAttendanceDetailReport report;
	private int weekCount;

	private static final java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("#.#");

	public void show(OfficerAttendanceDetailReport report) {
		if(report == null) {
			throw new IllegalArgumentException("Report cannot be null");
		}
		this.report = report;

		this.totalSessionsLabel.setText(String.valueOf(report.getTotalSessions()));
		this.totalHoursLateAndLeaveEarlyLabel.setText(decimalFormat.format(report.getTotalHoursNotWork()));

		setTimeRange(new TimeRange(report.getMonth(), report.getYear(), report.getMonthCount()));

		this.employeeInfoLabel.setText(report.getEmployee().getFullName() + " [" + report.getEmployee().getEmployeeCode() + "]");

		var fromDate = LocalDate.of(report.getYear(), report.getMonth(), 1);
		var toDate = fromDate.plusMonths(report.getMonthCount()).minusDays(1);
		var fromToRange = Duration.between(fromDate.atStartOfDay(), toDate.atStartOfDay()).toDays();

		var weekCount = (int) Math.ceil(fromToRange / 7.0);
		this.weekCount = weekCount;
		this.currentWeekLabel.setText("1/" + weekCount);

		var _fromDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		var _toDate = Date.from(fromDate.plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant());

		var attendances = report.getAttendances().stream()
				.filter(attendance -> attendance.getDate().compareTo(_fromDate) >= 0 && attendance.getDate().compareTo(_toDate) < 0)
				.map(attendance -> new OfficerAttendanceDTO(attendance)).toArray(OfficerAttendanceDTO[]::new);

		var observableAttendances = FXCollections.observableArrayList(attendances);

		this.attendanceLogTable.getItems().clear();
		this.attendanceLogTable.getItems().addAll(observableAttendances);
		nextWeekBtn.setDisable(weekCount == 1);
		previousWeekBtn.setDisable(true);
	}

}
