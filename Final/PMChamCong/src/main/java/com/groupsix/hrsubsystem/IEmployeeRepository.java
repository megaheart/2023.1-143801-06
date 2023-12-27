package com.groupsix.hrsubsystem;

import java.util.ArrayList;
import java.util.List;

public interface IEmployeeRepository {

	ArrayList<Employee> getEmployees();

	ArrayList<Employee> getEmployeesInDepartment(Department department);

	Employee getEmployeeByCode(String code);

	ArrayList<Employee> filterEmployeeByCode(String code, Department department);

	List<Employee> getEmployeesByListCodes(List<String> codes);

}
