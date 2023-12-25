package com.groupsix.request;

import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.hrsubsystem.Employee;
import com.groupsix.user.User;

import java.util.ArrayList;
import java.util.List;

public interface IRequestRepository {
    ArrayList<Request> getRequestOfEmployee(User user, int month, int year, int monthCount);

    void insertRequest(Request req);

    void insertMany(List<Request> requests);

    ArrayList<Request> getRequestOfEmployee(User user, int date, int month, int year, String employee_code);
}
