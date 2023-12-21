package com.groupsix.hrsubsystem;

import java.util.ArrayList;

public class HRSubsystemFactory {

	private Class<IEmployeeRepository> employeeRepoClass;

	private Class<IDepartmentRepository> departmentRepoClass;

	private static HRSubsystemFactory instance = new HRSubsystemFactory();

	private HRSubsystemFactory() {
	}

	public IDepartmentRepository createDepartmentRepository() {
		try{
			return departmentRepoClass.getDeclaredConstructor().newInstance();
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}
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


}
