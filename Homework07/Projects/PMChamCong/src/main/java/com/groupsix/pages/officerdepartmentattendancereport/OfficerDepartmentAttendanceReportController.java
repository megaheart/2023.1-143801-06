package com.groupsix.pages.officerdepartmentattendancereport;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.hrsubsystem.Department;
import com.groupsix.hrsubsystem.HRSubsystemFactory;
import com.groupsix.pages.FXRouter;
import com.groupsix.pages.employeeattendance.EmployeeAttendanceController;
import com.groupsix.report.OfficerAndAttendance;
import com.groupsix.report.OfficerAttendanceReport;
import com.groupsix.report.ReportHelper;
import com.groupsix.user.User;
import com.groupsix.hrsubsystem.Employee;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.user.UserService;

import java.time.LocalDate;
import java.util.ArrayList;

import java.io.File;

public class OfficerDepartmentAttendanceReportController {
	private OfficerDepartmentAttendanceReportView view;

	public OfficerDepartmentAttendanceReportController(OfficerDepartmentAttendanceReportView view) {
		this.view = view;
		view.setOnTimeRangeChangedHandler((e) -> {
			var timeRange = view.getTimeRange();
			var month = timeRange.getMonth();
			var year = timeRange.getYear();
			var monthCount = timeRange.getMonthCount();
			getReport(department, month, year, monthCount);
		});
		view.setOpenExportPanelHandler((e) -> {
			showExportPanel();
		});
		view.setOnEmployeeChooseHandler((sender, e) -> {
			var timeRange = view.getTimeRange();
			openEmployeeView(e, timeRange.getMonth(), timeRange.getYear(), timeRange.getMonthCount());
		});
		view.setOnSearchEmployeeByCodeHandler((sender, employeeCode) -> {
			var timeRange = view.getTimeRange();
			filterAttendancesBy(this.department, employeeCode, timeRange.getMonth(), timeRange.getYear(), timeRange.getMonthCount());
		});
		init();
	}

	private Department department;

	private OfficerAttendanceReport report;

	private User user;

	private ArrayList<Employee> employees;

	private AttendanceReportExportPanel exportPanel;

	private void init() {
		var user = UserService.getInstance().getCurrentUser();
		var employeeRepo = HRSubsystemFactory.getInstance().createEmployeeRepository();
		var employeeOfUser = employeeRepo.getEmployeeByCode(user.getEmployeeCode());
		var _department = HRSubsystemFactory.getInstance().createDepartmentRepository()
				.getDepartmentByCode(employeeOfUser.getDepartmentCode());
		this.department = _department;

		var now = LocalDate.now();

		getReport(_department, now.getMonthValue(), now.getYear(), 1);
	}

	public void exportReportAsFile(OfficerAttendanceReport report, String path, String format) {

	}

	public OfficerAndAttendance mergeOfficerAndAttendance(Employee officer, ArrayList<OfficerAttendance> officerAttendances) {
		OfficerAndAttendance officerAndAttendance = new OfficerAndAttendance();
		officerAndAttendance.setEmployee(officer);
		officerAndAttendance.setFullName(officer.getFullName());
		officerAndAttendance.setDepartmentName(this.department.getDepartmentName());

		var totalSession = 0;
		var totalHoursNotWork = 0.0;

		for(var attendance : officerAttendances) {
			if(attendance.isMorningSession()) {
				totalSession++;
			}

			if(attendance.isAfternoonSession()) {
				totalSession++;
			}

			totalHoursNotWork += attendance.getHoursEarlyLeave() + attendance.getHoursLate();
		}

		officerAndAttendance.setTotalSession(totalSession);
		officerAndAttendance.setHoursNotWork(totalHoursNotWork);

		return officerAndAttendance;
	}

	public OfficerAttendanceReport getReport(Department department, int month, int year, int monthCount) {
		var employeeRepo = HRSubsystemFactory.getInstance().createEmployeeRepository();
		var departmentRepo = HRSubsystemFactory.getInstance().createDepartmentRepository();
		var attendanceRepo = AttendanceFactory.getInstance().createRepository();

		var officerList = employeeRepo.getEmployeesInDepartment(department);
		var mergedOfficerAttendances = new ArrayList<OfficerAndAttendance>();

		for(var officer : officerList) {
			var officerAttendances = attendanceRepo.getAttendancesOfEmployee(user, officer, month, year, monthCount);
			var mergedOfficerAttendance = mergeOfficerAndAttendance(officer, officerAttendances);
			mergedOfficerAttendances.add(mergedOfficerAttendance);
		}

		var report = ReportHelper.summarizeReport(department, mergedOfficerAttendances, month, year, monthCount);
		this.report = report;

		view.show(report);

		return report;
	}

	public boolean checkPathExist(String path) {
		File f = new File(path);
		return f.exists() && !f.isDirectory();
	}

	public void filterAttendancesBy(Department department, String employeeCode, int month, int year, int monthCount) {
		var employeeRepo = HRSubsystemFactory.getInstance().createEmployeeRepository();
		var attendanceRepo = AttendanceFactory.getInstance().createRepository();

		var officers = employeeRepo.filterEmployeeByCode(employeeCode, department);
		var mergedOfficerAttendances = new ArrayList<OfficerAndAttendance>();

		for(var officer : officers) {
			var officerAttendances = attendanceRepo.getAttendancesOfEmployee(user, officer, month, year, monthCount);
			var mergedOfficerAttendance = mergeOfficerAndAttendance(officer, officerAttendances);
			mergedOfficerAttendances.add(mergedOfficerAttendance);
		}

		updateAttendanceList(officers, mergedOfficerAttendances);

		view.show(report);
	}

	public void updateAttendanceList(ArrayList<Employee> officers, ArrayList<OfficerAndAttendance> mergedOfficerAttendance) {
		var report = new OfficerAttendanceReport();
		report.setAttendances(mergedOfficerAttendance);
		report.setDepartment(this.department);
		report.setMonth(this.report.getMonth());
		report.setYear(this.report.getYear());
		report.setMonthCount(this.report.getMonthCount());
		report.setTotalSessions(this.report.getTotalSessions());
		report.setTotalHoursNotWork(this.report.getTotalHoursNotWork());
		report.setAverageSessions(this.report.getAverageSessions());
		report.setAverageHoursNotWork(this.report.getAverageHoursNotWork());

		this.report = report;
		this.view.show(report);
	}

	public void showExportPanel() {

	}

	public void closeExportPanel() {

	}

	public void openEmployeeView(Employee employee, int month, int year, int monthCount) {
		var employeeAttendanceCrtl = (EmployeeAttendanceController) FXRouter.goTo("employeeattendance");
		employeeAttendanceCrtl.openView(employee, month, year, monthCount);
	}

	public void open(int month, int year, int monthCount){
		var user = UserService.getInstance().getCurrentUser();
		var employeeRepo = HRSubsystemFactory.getInstance().createEmployeeRepository();
		var employeeOfUser = employeeRepo.getEmployeeByCode(user.getEmployeeCode());
		var _department = HRSubsystemFactory.getInstance().createDepartmentRepository()
				.getDepartmentByCode(employeeOfUser.getDepartmentCode());
		this.department = _department;

		getReport(_department, month, year, monthCount);
	}

}
