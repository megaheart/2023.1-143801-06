package com.groupsix.pages.officerdepartmentattendancereport;

import com.groupsix.base.IEventHandler;
import com.groupsix.base.TimeRange;
import com.groupsix.hrsubsystem.Employee;
import com.groupsix.report.OfficerAttendanceReport;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class OfficerDepartmentAttendanceReportView {
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
    private Label averageHoursLateAndLeaveEarlyLabel = new Label();

    @FXML
    private Label averageSessionsLabel = new Label();

    @FXML
    private Label totalRecordCountLabel = new Label();

    @FXML
    private Label currentPageLabel = new Label();

    @FXML
    private TextField pageWantToGoTxtBox = new TextField();

    @FXML
    private TableView<OfficerAndAttendanceDTO> attendanceLogTable = new TableView<OfficerAndAttendanceDTO>();

    @FXML
    public TableColumn<OfficerAndAttendanceDTO, String> fullnameCol = new TableColumn<>();
    @FXML
    public TableColumn<OfficerAndAttendanceDTO, String> employeeCodeCol = new TableColumn<>();
    @FXML
    public TableColumn<OfficerAndAttendanceDTO, String> departmentNameCol = new TableColumn<>();
    @FXML
    public TableColumn<OfficerAndAttendanceDTO, String> totalSessionsCol = new TableColumn<>();
    @FXML
    public TableColumn<OfficerAndAttendanceDTO, String> totalHoursLateAndLeaveEarlyCol = new TableColumn<>();

    @FXML
    public TableColumn<OfficerAndAttendanceDTO, String> optionsCol = new TableColumn<>();

    @FXML
    private Button nextPageBtn = new Button();

    @FXML
    private Button previousPageBtn = new Button();

    @FXML
    private Button clearSearchResultBtn = new Button();

    @FXML
    private TextField employeeCodeSearchTxtBox = new TextField();

    private TimeRange timeRange = new TimeRange(1, 1, 1);

    @FXML
    private void initialize() {
        timeTypeComboBox.getItems().addAll("Tháng", "Quý", "Năm");
        monthsComboBox.getItems().addAll("Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8",
                "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12");
        quarterComboBox.getItems().addAll("Quý I", "Quý II", "Quý III", "Quý IV");
        yearsComboBox.getItems().addAll("2020", "2021", "2022", "2023", "2024", "2025");
        setTimeRange(timeRange);

        fullnameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        employeeCodeCol.setCellValueFactory(new PropertyValueFactory<>("employeeCode"));
        departmentNameCol.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        totalSessionsCol.setCellValueFactory(new PropertyValueFactory<>("totalSessions"));
        totalHoursLateAndLeaveEarlyCol.setCellValueFactory(new PropertyValueFactory<>("totalHoursNotWork"));
        optionsCol.setCellFactory(cell -> new TableCell<OfficerAndAttendanceDTO, String>() {
            final Button btn = new Button("Xem chi tiết");

            {
                btn.setOnAction(event -> {
                    var officerAndAttendance = getTableView().getItems().get(getIndex());
                    chooseEmployee(officerAndAttendance.getEmployee());
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }

    @FXML
    private void onTimeTypeChanged(ActionEvent event) {
        int index = timeTypeComboBox.getSelectionModel().getSelectedIndex();

        int month = timeRange.getMonth() - 1;
        int year = timeRange.getYear();
        switch (index) {
            case 0:
                setTimeRange(new TimeRange(month + 1, year, 1));
                break;
            case 1:
                switch (timeRange.getMonthCount()) {
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
        if (onTimeRangeChangedHandler != null) {
            onTimeRangeChangedHandler.handle(event);
        }
    }

    @FXML
    private void onMonthChanged(ActionEvent event) {
        int month = monthsComboBox.getSelectionModel().getSelectedIndex() + 1;
        setTimeRange(new TimeRange(month, timeRange.getYear(), timeRange.getMonthCount()));
        if (onTimeRangeChangedHandler != null) {
            onTimeRangeChangedHandler.handle(event);
        }
    }

    @FXML
    private void onQuarterChanged(ActionEvent event) {
        int quarter = quarterComboBox.getSelectionModel().getSelectedIndex() + 1;
        setTimeRange(new TimeRange((quarter - 1) * 3 + 1, timeRange.getYear(), 3));
        if (onTimeRangeChangedHandler != null) {
            onTimeRangeChangedHandler.handle(event);
        }
    }

    @FXML
    private void onYearChanged(ActionEvent event) {
        int year = Integer.parseInt(yearsComboBox.getSelectionModel().getSelectedItem());
        setTimeRange(new TimeRange(timeRange.getMonth(), year, timeRange.getMonthCount()));
        if (onTimeRangeChangedHandler != null) {
            onTimeRangeChangedHandler.handle(event);
        }
    }

    @FXML
    private void clickExportExcel(ActionEvent event) {
        openExportPanel();
    }

    @FXML
    private void searchEmployeeByCode(ActionEvent event) {
        var employeeCode = employeeCodeSearchTxtBox.getText();
        if (employeeCode.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Lỗi nhập liệu");
            alert.setContentText("Vui lòng nhập mã nhân viên.");
            alert.showAndWait();
            employeeCodeSearchTxtBox.requestFocus();
            clearSearchResultBtn.setVisible(false);
            employeeCodeSearchTxtBox.requestFocus();
            return;
        }

        if (onSearchEmployeeByCodeHandler != null) {
            onSearchEmployeeByCodeHandler.handle(event, employeeCode);
            clearSearchResultBtn.setVisible(true);
        }
    }
    @FXML
    private void clearSearchResult(ActionEvent event) {
        employeeCodeSearchTxtBox.setText("");

        if (onSearchEmployeeByCodeHandler != null) {
            onSearchEmployeeByCodeHandler.handle(event, "");
        }

        clearSearchResultBtn.setVisible(false);
    }

    @FXML
    private void nextPage(ActionEvent event) {
        if (report == null) return;
        int currentPage = Integer.parseInt(currentPageLabel.getText().split("/")[0]);
        if (currentPage == pageCount) return;
        currentPage++;
        currentPageLabel.setText(currentPage + "/" + pageCount);

		var attendances = report.getAttendances()
                .subList((currentPage - 1) * pageSize, Math.min(currentPage * pageSize, report.getAttendances().size()))
                .stream()
                .map(OfficerAndAttendanceDTO::new).toArray(OfficerAndAttendanceDTO[]::new);

		this.attendanceLogTable.getItems().clear();
		this.attendanceLogTable.getItems().addAll(attendances);

        if (currentPage == pageCount) {
            nextPageBtn.setDisable(true);
        } else {
            nextPageBtn.setDisable(false);
        }

        if (currentPage == 1) {
            previousPageBtn.setDisable(true);
        } else {
            previousPageBtn.setDisable(false);
        }
    }

    @FXML
    private void previousPage(ActionEvent event) {
        if (report == null) return;

        int currentPage = Integer.parseInt(currentPageLabel.getText().split("/")[0]);
        if (currentPage == 1) return;
        currentPage--;
        currentPageLabel.setText(currentPage + "/" + pageCount);

		var attendances = report.getAttendances()
                .subList((currentPage - 1) * pageSize, Math.min(currentPage * pageSize, report.getAttendances().size()))
                .stream()
                .map(OfficerAndAttendanceDTO::new).toArray(OfficerAndAttendanceDTO[]::new);

		this.attendanceLogTable.getItems().clear();
		this.attendanceLogTable.getItems().addAll(attendances);

        if (currentPage == pageCount) {
            nextPageBtn.setDisable(true);
        } else {
            nextPageBtn.setDisable(false);
        }

        if (currentPage == 1) {
            previousPageBtn.setDisable(true);
        } else {
            previousPageBtn.setDisable(false);
        }
    }

    @FXML
    private void goToPage(ActionEvent event) {
        int page = 0;
        try {
            page = Integer.parseInt(pageWantToGoTxtBox.getText());
        } catch (NumberFormatException e) {
            pageWantToGoTxtBox.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Lỗi nhập liệu");
            alert.setContentText("Vui lòng nhập số trang hợp lệ, không được bỏ trống.");
            alert.showAndWait();
            pageWantToGoTxtBox.requestFocus();
            return;
        }

        if (page < 1 || page > pageCount) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Lỗi nhập liệu");
            alert.setContentText("Vui lòng nhập số trang trong khoảng từ 1 đến " + pageCount);
            alert.showAndWait();
            pageWantToGoTxtBox.requestFocus();
            return;
        }

        currentPageLabel.setText(page + "/" + pageCount);

		var attendances = report.getAttendances()
                .subList((page - 1) * pageSize, Math.min(page * pageSize, report.getAttendances().size()))
                .stream()
                .map(OfficerAndAttendanceDTO::new).toArray(OfficerAndAttendanceDTO[]::new);
		this.attendanceLogTable.getItems().clear();
		this.attendanceLogTable.getItems().addAll(attendances);

        if (page == pageCount) {
            nextPageBtn.setDisable(true);
        } else {
            nextPageBtn.setDisable(false);
        }

        if (page == 1) {
            previousPageBtn.setDisable(true);
        } else {
            previousPageBtn.setDisable(false);
        }

    }

    private EventHandler<ActionEvent> onTimeRangeChangedHandler;
    private EventHandler<ActionEvent> openExportPanelHandler;
    private IEventHandler<Object, Employee> onEmployeeChooseHandler;
	private IEventHandler<ActionEvent, String> onSearchEmployeeByCodeHandler;
//	private IEventHandler<ActionEvent, Integer> goToPageHandler;

    public void setOnTimeRangeChangedHandler(EventHandler<ActionEvent> onTimeRangeChangedHandler) {
        this.onTimeRangeChangedHandler = onTimeRangeChangedHandler;
    }

    public void setOpenExportPanelHandler(EventHandler<ActionEvent> openExportPanelHandler) {
        this.openExportPanelHandler = openExportPanelHandler;
    }

    public void setOnEmployeeChooseHandler(IEventHandler<Object, Employee> onEmployeeChooseHandler) {
        this.onEmployeeChooseHandler = onEmployeeChooseHandler;
    }

    public void setOnSearchEmployeeByCodeHandler(IEventHandler<ActionEvent, String> onSearchEmployeeByCodeHandler) {
        this.onSearchEmployeeByCodeHandler = onSearchEmployeeByCodeHandler;
    }
//
//	public void setGoToPageHandler(IEventHandler<ActionEvent, Integer> goToPageHandler) {
//		this.goToPageHandler = goToPageHandler;
//	}

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        if (timeRange == null) {
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

    private OfficerAttendanceReport report;
    private int pageCount;
    private final int pageSize = 14; // 14

    private static final java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("#.#");

    public void show(OfficerAttendanceReport report) {
        if (report == null) {
            throw new IllegalArgumentException("Report cannot be null");
        }
        this.report = report;

        this.totalSessionsLabel.setText(String.valueOf(report.getTotalSessions()));
        this.totalHoursLateAndLeaveEarlyLabel.setText(decimalFormat.format(report.getTotalHoursNotWork()));
        this.averageHoursLateAndLeaveEarlyLabel.setText(decimalFormat.format(report.getAverageHoursNotWork()));
        this.averageSessionsLabel.setText(decimalFormat.format(report.getAverageSessions()));

        this.totalRecordCountLabel.setText(String.valueOf(report.getAttendances().size()));
        pageCount = (int) Math.ceil(report.getAttendances().size() * 1.0 / pageSize);
        this.currentPageLabel.setText("1/" + pageCount);

        setTimeRange(new TimeRange(report.getMonth(), report.getYear(), report.getMonthCount()));

        var attendances = report.getAttendances().subList(0, Math.min(pageSize, report.getAttendances().size()))
                .stream()
                .map(OfficerAndAttendanceDTO::new).toArray(OfficerAndAttendanceDTO[]::new);

        var observableAttendances = FXCollections.observableArrayList(attendances);

        this.attendanceLogTable.getItems().clear();
        this.attendanceLogTable.getItems().addAll(observableAttendances);
        nextPageBtn.setDisable(pageCount <= 1);
        previousPageBtn.setDisable(true);
    }

    public void openExportPanel() {
        if (openExportPanelHandler != null) {
            openExportPanelHandler.handle(new ActionEvent());
        }
    }

    public void chooseEmployee(Employee employee) {
        if (onEmployeeChooseHandler != null) {
            onEmployeeChooseHandler.handle(this, employee);
        }
    }

    public void chooseMonth(int month, int year) {
        setTimeRange(new TimeRange(month, year, 1));
    }

    public void chooseQuarter(int quarter, int year) {
        setTimeRange(new TimeRange((quarter - 1) * 3 + 1, year, 3));
    }

    public void chooseYear(int year) {
        setTimeRange(new TimeRange(1, year, 12));
    }

}
