package com.groupsix.hrsubsystem;

import java.util.ArrayList;

public class HRSubsystemFactory {

	private Class<IEmployeeRepository> employeeRepoClass;

	private Class<IDepartmentRepository> departmentRepoClass;

	private static HRSubsystemFactory instance = new HRSubsystemFactory();

	public IDepartmentRepository createDepartmentRepository() {
		return null;
	}

	public IEmployeeRepository createEmployeeRepository() {
		try{
			return employeeRepoClass.getDeclaredConstructor().newInstance();
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}
	}

	public void registerDepartmentRepository(Class repoClass) {
		this.departmentRepoClass = repoClass;
	}

	public void registerEmployeeRepository(Class repoClass) {
		this.employeeRepoClass = repoClass;
	}

	public static HRSubsystemFactory getInstance() {
		return instance;
	}

	public ArrayList<Employee> getEmployees() {
		return null;
	}

	public ArrayList<Employee> getEmployeesInDepartment(Department department) {
		return null;
	}

	public Employee getEmployeeByCode(String code) {
		return null;
	}

	public ArrayList<Employee> filterEmployeeByCode(String code, Department department) {
		return null;
	}

	public ArrayList<Department> getDepartments() {
		return null;
	}

	public Department getDepartmentByCode(String code) {
		return null;
	}

}
