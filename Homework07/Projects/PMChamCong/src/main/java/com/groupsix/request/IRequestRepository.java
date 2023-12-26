package com.groupsix.request;

import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.hrsubsystem.Employee;
import com.groupsix.user.User;

import java.util.ArrayList;
import java.util.List;

public interface IRequestRepository {
    ArrayList<Request> getRequestOfEmployee(User user, int month, int year, int monthCount);

    void insertRequest(Request req);


    ArrayList<Request> getRequestOfEmployee(User user, int date, int month, int year, String employee_code);

    ArrayList<Request> getRequestNotification(Employee employee);
    ArrayList<Request> getRequestOfEmployee(User user, int date, int month, int year, String employee_code, int status);

    void updateRequest(User user, int requestId, int status, String response);

    Request getRequest(int id);

}
