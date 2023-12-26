package com.groupsix.pages.officerattendancedetail;

import com.groupsix.attendance.OfficerAttendance;
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

                    boolean morningSession = this.viewDetail.morningRequest.getValue().equals("Có");
                    boolean afternoonSession = this.viewDetail.afternoonRequest.getValue().equals("Có");
                    double hoursLate = Double.parseDouble(this.viewDetail.lateRequest.getText());
                    double hoursEarlyLeave = Double.parseDouble(this.viewDetail.earlyLeaveRequest.getText());
                    String reason = this.viewDetail.reasonRequest.getText();
                    //Lấy thời gian của hệ thống
                    //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    Date now = new Date();
                    Request request = new Request();
                    request.setEmployeeCode(UserService.getInstance().getCurrentUser().getEmployeeCode());
                    request.setDate(now);
                    request.setHoursLate(hoursLate);
                    request.setHoursEarlyLeave(hoursEarlyLeave);
                    request.setMorningSession(morningSession);
                    request.setAfternoonSession(afternoonSession);
                    request.setReason(reason);
                    insertRequest(request);
                    System.out.println("Người dùng đã xác nhận");
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
            this.viewDetail.morningSession.setText(attendance.isMorningSession() ? "Có" : "Không");
            this.viewDetail.afternoonSession.setText(attendance.isAfternoonSession() ? "Có" : "Không");
            this.viewDetail.lateView.setText(String.valueOf(attendance.getHoursLate()));
            this.viewDetail.earlyLeaveView.setText(String.valueOf(attendance.getHoursEarlyLeave()));

    }

    private void insertRequest(Request request){
        var repo = RequestFactory.getInstance().createRepository();
        var user = UserService.getInstance().getCurrentUser();
        repo.insertRequest(request);
    }
}
