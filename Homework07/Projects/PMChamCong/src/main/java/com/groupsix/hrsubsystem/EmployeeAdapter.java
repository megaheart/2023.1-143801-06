package com.groupsix.hrsubsystem;

import com.groupsix.base.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;

import java.util.ArrayList;

public class EmployeeAdapter implements IEmployeeRepository {
    private final Dao<Employee, String> employeeDao;

    public EmployeeAdapter() {
        employeeDao = DatabaseHelper.createDAO(Employee.class);
    }

    public ArrayList<Employee> getEmployees() {
        try {
            return (ArrayList<Employee>) employeeDao.queryForAll();
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Employee> getEmployeesInDepartment(Department department) {
        try {
            return (ArrayList<Employee>) employeeDao.queryForEq("departmentCode", department.getDepartmentCode());
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee getEmployeeByCode(String code) {
        try {
            return employeeDao.queryForId(code);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Employee> filterEmployeeByCode(String code) {
        try {
            QueryBuilder<Employee, String> queryBuilder = employeeDao.queryBuilder();
            var query = queryBuilder.where()
                    .like("employeeCode", "%" + code + "%")
                    .prepare();
            return (ArrayList<Employee>) employeeDao.query(query);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Employee> filterEmployeeByCode(String code, Department department) {
        try {
            QueryBuilder<Employee, String> queryBuilder = employeeDao.queryBuilder();
            var query = queryBuilder.where()
                    .like("employeeCode", "%" + code + "%")
                    .and()
                    .eq("departmentCode", department.getDepartmentCode())
                    .prepare();
            return (ArrayList<Employee>) employeeDao.query(query);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
