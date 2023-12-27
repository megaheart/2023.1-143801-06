package com.groupsix.hrsubsystem;

import java.util.ArrayList;

public interface IDepartmentRepository {

	ArrayList<Department> getDepartments();

	Department getDepartmentByCode(String code);

}
