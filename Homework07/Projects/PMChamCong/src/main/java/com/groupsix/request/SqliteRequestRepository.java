package com.groupsix.request;

import com.groupsix.attendance.IOfficerAttendanceRepository;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.base.DatabaseHelper;
import com.groupsix.user.User;
import com.groupsix.hrsubsystem.Employee;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.SelectArg;
import org.apache.poi.ss.usermodel.DateUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqliteRequestRepository implements IRequestRepository {

    private final Dao<Request, Integer> dao;

    public SqliteRequestRepository() {
        dao = DatabaseHelper.createDAO(Request.class);
    }

    @Override
    public ArrayList<Request> getRequestOfEmployee(User user, int month, int year, int monthCount) {
        LocalDate fromDate = LocalDate.of(year, month, 1);
        LocalDate toDate = fromDate.plusMonths(monthCount).minusDays(1);

        Date _fromDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date _toDate = Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        var queryBuilder = dao.queryBuilder();
        try {
            SelectArg fromDateArg = new SelectArg(_fromDate);
            SelectArg toDateArg = new SelectArg(_toDate);
            queryBuilder.where().between("date", fromDateArg, toDateArg);

            var statement = queryBuilder.prepare();

            System.out.println(statement.getStatement());

            return (ArrayList<Request>) dao.query(statement);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void insertMany(List<Request> requests) {
        try {
            String sql = "INSERT INTO Request (employeeCode, date, hoursEarlyLeave, hoursLate, morningSession, afternoonSession, reason, status) VALUES ";

            List<String> values = new ArrayList<>();
            for (Request req : requests) {
                String s = String.format("('%s', '%s', %f, %f, %d, %d %s, %d)",
                        req.getEmployeeCode(),
                        req.getDate(),
                        req.getHoursEarlyLeave(),
                        req.getHoursLate(),
                        req.isMorningSession() ? 1 : 0,
                        req.isAfternoonSession() ? 1 : 0,
                        req.getReason(),
                        req.getStatus());
                values.add(s);
            }

            sql += String.join(",", values);

            dao.executeRaw(sql);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}