package com.groupsix.hrsubsystem;

import com.groupsix.base.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.util.ArrayList;

public class DepartmentAdapter implements IDepartmentRepository {

	private final Dao<Department, String> dao;

	public DepartmentAdapter() {
		dao = DatabaseHelper.createDAO(Department.class);
	}
	public ArrayList<Department> getDepartments() {
		try {
			return (ArrayList<Department>) dao.queryForAll();
		} catch (java.sql.SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Department getDepartmentByCode(String code) {
		try {
			return dao.queryForId(code);
		} catch (java.sql.SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
