package com.groupsix.request;

import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.hrsubsystem.Employee;
import com.groupsix.user.User;

import java.util.ArrayList;
import java.util.List;

public interface IRequestRepository {
    ArrayList<Request> getRequestOfEmployee(User user, int month, int year, int monthCount);
    void insertMany(List<Request> requests);

}