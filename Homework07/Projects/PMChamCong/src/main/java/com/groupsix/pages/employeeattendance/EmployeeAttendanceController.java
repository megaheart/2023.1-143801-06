package com.groupsix.pages.employeeattendance;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.base.TimeRange;
import com.groupsix.hrsubsystem.Employee;
import com.groupsix.report.ReportHelper;
import com.groupsix.user.User;
import com.groupsix.report.OfficerAttendanceDetailReport;
import com.groupsix.report.OfficerAttendanceReport;
import com.groupsix.user.UserService;

public class EmployeeAttendanceController {
	private EmployeeAttendanceView view;
	public EmployeeAttendanceController(EmployeeAttendanceView view) {
		this.view = view;

		view.setRefreshTableHandler((e) -> {
			getEmployeeReport(employee, timeRange.getMonth(), timeRange.getYear(), timeRange.getMonthCount());
		});

		view.setOnTimeRangeChangedHandler((e) -> {
			timeRange = view.getTimeRange();
			getEmployeeReport(employee, timeRange.getMonth(), timeRange.getYear(), timeRange.getMonthCount());
		});
	}

	private Employee employee;

	private User user;

	private OfficerAttendanceDetailReport report;

	private TimeRange timeRange;

	public void chooseMonth(int month, int year) {
		getEmployeeReport(employee, month, year, 1);
	}

	public OfficerAttendanceDetailReport getEmployeeReport(Employee employee, int month, int year, int monthCount) {
		var attendanceRepo = AttendanceFactory.getInstance().createRepository();
		var attendances = attendanceRepo.getAttendancesOfEmployee(user, employee, month, year, monthCount);

		var report = ReportHelper.summarizeReport(employee, attendances, month, year, monthCount);

		view.show(report);

		this.report = report;

		return null;
	}

	public void openView(Employee employee, int month, int year, int monthCount) {
		this.employee = employee;
		this.user = UserService.getInstance().getCurrentUser();
		var attendanceRepo = AttendanceFactory.getInstance().createRepository();
		var attendances = attendanceRepo.getAttendancesOfEmployee(user, employee, month, year, monthCount);

		var report = ReportHelper.summarizeReport(employee, attendances, month, year, monthCount);

		this.report = report;

		view.show(report);
	}

}
