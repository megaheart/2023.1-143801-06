package com.groupsix.pages.officerattendancedetail;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.hrsubsystem.Employee;
import com.groupsix.hrsubsystem.HRSubsystemFactory;
import com.groupsix.pages.FXRouter;
import com.groupsix.request.Request;
import com.groupsix.request.RequestFactory;
import com.groupsix.user.UserService;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class OfficerDetailController implements Initializable {

    private OfficerDetailView viewDetail;
    public int attendanceLog;

    public OfficerDetailController(OfficerDetailView viewDetail) {
        this.viewDetail = viewDetail;
        initialize(null, null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Xử lý nút đóng
        this.viewDetail.closeBtn.setOnAction(event -> {
            //this.viewDetail.closeBtn.getScene().getWindow().hide();
            var ctrl = (OfficerHomeController) FXRouter.goTo("officerattendancedetail");
            /*String dateLabel = this.viewDetail.dateView.getText();
            String[] dateInfo = dateLabel.split(" - ");
            int day = Integer.parseInt(dateInfo[0]);
            int month = Integer.parseInt(dateInfo[1]);
            int year = Integer.parseInt(dateInfo[2]);
            ctrl.handleDetailBack(day, month, year);*/

        });

        // Xử lý nút gửi yêu cầu
        this.viewDetail.requestBtn.setOnAction(event -> {
            //Lấy giá trị của LateRequest
            String lateRequest = this.viewDetail.lateRequest.getText();
            String earlyLeaveRequest = this.viewDetail.earlyLeaveRequest.getText();
            String reasonRequest = this.viewDetail.reasonRequest.getText();
            if(lateRequest.isEmpty () || !isNonNegativeDouble(lateRequest) || earlyLeaveRequest.isEmpty() || !isNonNegativeDouble(earlyLeaveRequest) || reasonRequest.isEmpty()) {
                if(lateRequest.isEmpty() || !isNonNegativeDouble(lateRequest)){
                    this.viewDetail.lateRequest.setStyle("-fx-border-color: red");
                }
                if(earlyLeaveRequest.isEmpty() || !isNonNegativeDouble(earlyLeaveRequest)){
                    this.viewDetail.earlyLeaveRequest.setStyle("-fx-border-color: red");
                }
                if(reasonRequest.isEmpty()){
                    this.viewDetail.reasonRequest.setStyle("-fx-border-color: red");
                }
            }else{
            /*    boolean OK = true;
                if(this.attendanceLog == -1){
                    String[] dateString = this.viewDetail.dateView.getText().split("/");
                    int day = Integer.parseInt(dateString[0]);
                    int month = Integer.parseInt(dateString[1]);
                    int year = Integer.parseInt(dateString[2]);
                    var repo = RequestFactory.getInstance().createRepository();
                    var user = UserService.getInstance().getCurrentUser();
                    OfficerAttendance attendance = repo.getAttendance(user, day, month, year);
                    if(attendance == null){
                        OK = false;
                        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                        errorDialog.setTitle("Lỗi");
                        errorDialog.setHeaderText("Không thể yêu cầu thay đổi");
                        errorDialog.setContentText("Bạn không thể yêu cầu thay đổi vào ngày này");
                        errorDialog.showAndWait();
                    }
                }*/

                this.viewDetail.lateRequest.setStyle("-fx-border-color: none");
                this.viewDetail.earlyLeaveRequest.setStyle("-fx-border-color: none");
                this.viewDetail.reasonRequest.setStyle("-fx-border-color: none");
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDialog.setTitle("Xác nhận");
                confirmationDialog.setHeaderText("Xác nhận thực hiện hành động này?");
                confirmationDialog.setContentText("Bạn có chắc chắn muốn yêu cầu thay đổi không?");

                // Tạo các nút xác nhận và hủy
                ButtonType confirmButton = new ButtonType("Xác nhận");
                ButtonType cancelButton = new ButtonType("Hủy");

                confirmationDialog.getButtonTypes().setAll(confirmButton, cancelButton);

                // Hiển thị dialog và chờ người dùng phản hồi
                Optional<ButtonType> result = confirmationDialog.showAndWait();

                // Xử lý phản hồi từ người dùng
                if (result.isPresent() && result.get() == confirmButton) {
                    // Truy vấn logID
                    /*String[] dateString = this.viewDetail.dateView.getText().split(" - ");
                    int day = Integer.parseInt(dateString[0]);
                    int month = Integer.parseInt(dateString[1]);
                    int year = Integer.parseInt(dateString[2]);
                    var repo = RequestFactory.getInstance().createRepository();
                    var user = UserService.getInstance().getCurrentUser();
                    OfficerAttendance attendance = repo.getAttendance(user, day, month, year);*/


                    boolean morningSession = this.viewDetail.morningRequest.getValue().equals("Có");
                    boolean afternoonSession = this.viewDetail.afternoonRequest.getValue().equals("Có");
                    double hoursLate = Double.parseDouble(this.viewDetail.lateRequest.getText());
                    double hoursEarlyLeave = Double.parseDouble(this.viewDetail.earlyLeaveRequest.getText());
                    String reason = this.viewDetail.reasonRequest.getText();
                    //Lấy thời gian của hệ thống
                    //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    Request request = new Request();
                    request.setEmployeeCode(UserService.getInstance().getCurrentUser().getEmployeeCode());
                    request.setHoursLate(hoursLate);
                    request.setHoursEarlyLeave(hoursEarlyLeave);
                    request.setMorningSession(morningSession);
                    request.setAfternoonSession(afternoonSession);
                    request.setReason(reason);
                    request.setStatus(1);

                    var repo = AttendanceFactory.getInstance().createRepository();
                    var user = UserService.getInstance().getCurrentUser();
                    var employee = HRSubsystemFactory.getInstance().createEmployeeRepository().getEmployeeByCode(user.getEmployeeCode());
                    String dateLabel = this.viewDetail.dateView.getText();
                    String[] dateInfo = dateLabel.split("/");
                    int day = Integer.parseInt(dateInfo[0]);
                    int month = Integer.parseInt(dateInfo[1]);
                    int year = Integer.parseInt(dateInfo[2]);
                    boolean OK = checkSendRequest(employee, this.attendanceLog, year, month, day);
                    if(!OK){
                        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                        errorDialog.setTitle("Lỗi");
                        errorDialog.setHeaderText("Không thể yêu cầu thay đổi");
                        errorDialog.setContentText("Yêu cầu thay đổi đã được gửi trước đó!");
                        errorDialog.showAndWait();

                    }
                    {
                        if (this.attendanceLog != -1) {
                            System.out.println(this.attendanceLog);
                            Date now = new Date();
                            request.setDate(now);
                            request.setLogAttendanceId(attendanceLog);
                        } else {

                            //Date dateLog = new Date(year - 1900, month - 1, day);
                            LocalDate dateLog = LocalDate.of(year, month, day);
                            System.out.print("HelloVietNam" + dateLog);
                            Date _dateLog = Date.from(dateLog.atStartOfDay().toInstant(java.time.ZoneOffset.UTC));
                            System.out.println(_dateLog);
                            request.setDate(_dateLog);
                            request.setLogAttendanceId(-1);
                        }
                        insertRequest(request);

                        System.out.println("Người dùng đã xác nhận");
                    }
                    // Thực hiện hành động khi người dùng xác nhận
                } else {
                    System.out.println("Người dùng đã hủy");
                    // Thực hiện hành động khi người dùng hủy
                }
            }
        });

        // Xử lý comboBox morningRequest
        this.viewDetail.morningRequest.getItems().addAll("Có", "Không");

        // Xử lý comboBox affternoonRequest
        this.viewDetail.afternoonRequest.getItems().addAll("Có", "Không");

        // Xử lý textField lateRequest

    }

    public void setDateLabel(String date){
        this.viewDetail.dateView.setText(date);
    }

    public boolean isNonNegativeDouble(String input){
        try{
            double d = Double.parseDouble(input);
            if(d < 0 || d > 24){
                return false;
            }
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public void settup(OfficerAttendance attendance){
        System.out.println(attendance);
        if(attendance != null) {
            this.viewDetail.morningSession.setText(attendance.isMorningSession() ? "Có" : "Không");
            this.viewDetail.afternoonSession.setText(attendance.isAfternoonSession() ? "Có" : "Không");
            this.viewDetail.lateView.setText(String.valueOf(attendance.getHoursLate()));
            this.viewDetail.earlyLeaveView.setText(String.valueOf(attendance.getHoursEarlyLeave()));
            this.attendanceLog = attendance.getId();
            this.viewDetail.morningRequest.setValue(attendance.isMorningSession() ? "Có" : "Không");
            this.viewDetail.afternoonRequest.setValue(attendance.isAfternoonSession() ? "Có" : "Không");
            this.viewDetail.lateRequest.setText(String.valueOf(attendance.getHoursLate()));
            this.viewDetail.earlyLeaveRequest.setText(String.valueOf(attendance.getHoursEarlyLeave()));
        }else {
            this.viewDetail.morningSession.setText("Không");
            this.viewDetail.afternoonSession.setText("Không");
            this.viewDetail.lateView.setText("0.0");
            this.viewDetail.earlyLeaveView.setText("0.0");
            this.attendanceLog = -1;
            this.viewDetail.morningRequest.setValue("Không");
            this.viewDetail.afternoonRequest.setValue("Không");
            this.viewDetail.lateRequest.setText("0.0");
            this.viewDetail.earlyLeaveRequest.setText("0.0");
        }

    }

    private void insertRequest(Request request){
        var repo = RequestFactory.getInstance().createRepository();
        var user = UserService.getInstance().getCurrentUser();
        repo.insertRequest(request);
    }

    public boolean checkSendRequest(Employee e, int logID, int yearLog, int monthLog, int dayLog){
        var repo = RequestFactory.getInstance().createRepository();
        var user = UserService.getInstance().getCurrentUser();
        if(logID != -1){
            ;
            Request request = repo.getOfficerRequest(logID);
            if(request != null){
                return false;
            }
        }else{
            var requests = repo.getSendRequest(e, monthLog, yearLog, 1);
            for(Request request : requests){
                System.out.println("Dòng 243");
                System.out.println(request.getDate().getDate());
                System.out.println(dayLog);
                if(request.getDate().getDate() == dayLog){
                    return false;
                }
            }
        }
        return true;
    }
}
