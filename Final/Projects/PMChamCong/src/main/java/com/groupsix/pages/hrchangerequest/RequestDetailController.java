package com.groupsix.pages.hrchangerequest;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.pages.FXRouter;
import com.groupsix.request.Request;
import com.groupsix.request.RequestFactory;
import com.groupsix.user.UserService;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.*;

public class RequestDetailController implements Initializable {
    private RequestDetailView viewDetail;

    public RequestDetailController(RequestDetailView viewDetail) {
        this.viewDetail = viewDetail;
        initialize(null, null);
    }

    public void initialize(URL location, ResourceBundle resources){


        // Close Button
        this.viewDetail.closeBtn.setOnAction(event -> {
            var ctrl = (RequestHomeController) FXRouter.goTo("requesthomeview");
        });

        // Reject Button Functionality
        this.viewDetail.rejectBtn.setOnAction(event -> {
            // Check status of request
            if (this.viewDetail.statusLabel.getText().equals("Từ chối")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Yêu cầu đã bị từ chối trước đó");
                alert.showAndWait();
                return;
            }
            else if (this.viewDetail.statusLabel.getText().equals("Đã duyệt")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Yêu cầu đã được chấp nhận trước đó");
                alert.showAndWait();
                return;
            }
            else if (this.viewDetail.statusLabel.getText().equals("Chờ duyệt")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận");
                alert.setHeaderText("Xác nhận thực hiện hành động này?");
                alert.setContentText("Bạn có chắc chắn muốn từ chối yêu cầu này?");

                // Create confirm and cancel buttons
                ButtonType confirmButton = new ButtonType("Xác nhận");
                ButtonType cancelButton = new ButtonType("Hủy");
                alert.getButtonTypes().setAll(confirmButton, cancelButton);

                // Show confirmation dialog
                Optional<ButtonType> result = alert.showAndWait();

                // If user confirms, reject request
                if (result.isPresent() && result.get() == confirmButton) {
                    System.out.println("Confirm reject");
                    // Update request
                    String response = this.viewDetail.responseTextArea.getText();
                    int requestId = Integer.parseInt(this.viewDetail.requestIDLabel.getText());
                    int status = 0;
                    var repo = RequestFactory.getInstance().createRepository();
                    var user = UserService.getInstance().getCurrentUser();
                    repo.updateRequest(user, requestId, status, response);
                } else {
                    System.out.println("Cancel");
                }
            }

        });
        this.viewDetail.acceptBtn.setOnAction(event -> {
            // Check status of request
            if (this.viewDetail.statusLabel.getText().equals("Từ chối")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Yêu cầu đã bị từ chối trước đó");
                alert.showAndWait();
                return;
            }
            else if (this.viewDetail.statusLabel.getText().equals("Đã duyệt")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Yêu cầu đã được chấp nhận trước đó");
                alert.showAndWait();
                return;
            }
            else if (this.viewDetail.statusLabel.getText().equals("Chờ duyệt")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận");
                alert.setHeaderText("Xác nhận thực hiện hành động này?");
                alert.setContentText("Bạn có chắc chắn muốn chấp nhận yêu cầu này?");

                // Create confirm and cancel buttons
                ButtonType confirmButton = new ButtonType("Xác nhận");
                ButtonType cancelButton = new ButtonType("Hủy");
                alert.getButtonTypes().setAll(confirmButton, cancelButton);

                // Show confirmation dialog
                Optional<ButtonType> result = alert.showAndWait();

                // If user confirms, accept request
                if (result.isPresent() && result.get() == confirmButton) {
                    System.out.println("Confirm accept");

                    // Update log attendance
                    int logAttendanceId = Integer.parseInt(this.viewDetail.logAttendanceLabel.getText());
                    boolean morningSession = this.viewDetail.morningSessionComboBox.getValue().equals("Có");
                    boolean afternoonSession = this.viewDetail.afternoonSessionComboBox.getValue().equals("Có");
                    double hoursLate = Double.parseDouble(this.viewDetail.hoursLateTextField.getText());
                    double hoursEarlyLeave = Double.parseDouble(this.viewDetail.hoursEarlyLeaveTextField.getText());
                    String employeeCode = this.viewDetail.employeeCodeLabel.getText();
                    String date = this.viewDetail.dateLabel.getText();
                    String[] dateInfo = date.split("/");
                    int day = Integer.parseInt(dateInfo[0]);
                    int month = Integer.parseInt(dateInfo[1]);
                    int year = Integer.parseInt(dateInfo[2]);
                    Date attendanceDate = new Date(year, month, day);
                    var repo_attendance = AttendanceFactory.getInstance().createRepository();
                    if (logAttendanceId == -1) {
                        // Insert new log attendance
                        List<OfficerAttendance> insertAttendanceList = new ArrayList<>();
                        OfficerAttendance attendance = new OfficerAttendance();
                        attendance.setEmployeeCode(employeeCode);
                        attendance.setDate(attendanceDate);
                        attendance.setHoursLate(hoursLate);
                        attendance.setHoursEarlyLeave(hoursEarlyLeave);
                        attendance.setMorningSession(morningSession);
                        attendance.setAfternoonSession(afternoonSession);
                        insertAttendanceList.add(attendance);
                        repo_attendance.insertMany(insertAttendanceList);
                    } else {
                        // Update log attendance
                        repo_attendance.updateAttendance(morningSession, afternoonSession, hoursLate, hoursEarlyLeave, logAttendanceId);
                    }

                    // Update request
                    String response = this.viewDetail.responseTextArea.getText();
                    int requestId = Integer.parseInt(this.viewDetail.requestIDLabel.getText());
                    int status = 2;
                    var repo = RequestFactory.getInstance().createRepository();
                    var user = UserService.getInstance().getCurrentUser();
                    repo.updateRequest(user, requestId, status, response);
                } else {
                    System.out.println("Cancel");
                }
            }
        });

        this.viewDetail.morningSessionComboBox.getItems().addAll("Có", "Không");
        this.viewDetail.afternoonSessionComboBox.getItems().addAll("Có", "Không");
    }

    public void setup(RequestRow row){
        String requestId = row.getRequestId();
        this.viewDetail.requestIDLabel.setText(requestId);

        String employeeCode = row.getEmployeeCode();
        this.viewDetail.employeeCodeLabel.setText(employeeCode);

        String status = row.getStatus();
        this.viewDetail.statusLabel.setText(status);

        String date = row.getDate();
        this.viewDetail.dateLabel.setText(date);

        int logAttendanceId = row.getLogAttendanceId();
        this.viewDetail.logAttendanceLabel.setText(String.valueOf(logAttendanceId));

        if (logAttendanceId == -1){
            // Setup old attendance
            this.viewDetail.oldAfternoonSessionLabel.setText("Không");
            this.viewDetail.oldMorningSessionLabel.setText("Không");
            this.viewDetail.oldHoursEarlyLeaveLabel.setText("0");
            this.viewDetail.oldHoursLateLabel.setText("0");
            this.viewDetail.reasonTextArea.setText("Thêm mới yêu cầu chấm công");
        } else{
            // Setup old attendance
            var repo = AttendanceFactory.getInstance().createRepository();
            OfficerAttendance old_attendance = repo.getAttendance(logAttendanceId);
            this.viewDetail.oldAfternoonSessionLabel.setText(old_attendance.isAfternoonSession() ? "Có" : "Không");
            this.viewDetail.oldMorningSessionLabel.setText(old_attendance.isMorningSession() ? "Có" : "Không");
            this.viewDetail.oldHoursEarlyLeaveLabel.setText(String.valueOf(old_attendance.getHoursEarlyLeave()));
            this.viewDetail.oldHoursLateLabel.setText(String.valueOf(old_attendance.getHoursLate()));
        }

        var repo = RequestFactory.getInstance().createRepository();
        Request current_request = repo.getRequest(Integer.parseInt(requestId));

        this.viewDetail.morningSessionComboBox.setValue(current_request.isMorningSession() ? "Có" : "Không");
        this.viewDetail.afternoonSessionComboBox.setValue(current_request.isAfternoonSession() ? "Có" : "Không");
        this.viewDetail.hoursEarlyLeaveTextField.setText(String.valueOf(current_request.getHoursEarlyLeave()));
        this.viewDetail.hoursLateTextField.setText(String.valueOf(current_request.getHoursLate()));

        if (logAttendanceId != -1){
            // Set up reason
            this.viewDetail.reasonTextArea.setText(current_request.getReason());
        }
        this.viewDetail.reasonTextArea.setEditable(false);

    }
}
