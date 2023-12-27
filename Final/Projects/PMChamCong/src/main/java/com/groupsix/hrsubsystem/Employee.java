package com.groupsix.hrsubsystem;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Employee")
public class Employee {

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	@DatabaseField()
	private String fullName;
	@DatabaseField(id = true)
	private String employeeCode;
	@DatabaseField()
	private String departmentCode;

	@DatabaseField()
	private String positionName;

	@Override
	public String toString() {
		return fullName + " (" + employeeCode + ")";
	}

}
