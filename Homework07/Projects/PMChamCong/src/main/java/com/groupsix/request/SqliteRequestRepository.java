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

    public ArrayList<Request> getRequestOfEmployee(User user, int date, int month, int year, String employee_code){
        var queryBuilder = dao.queryBuilder();
        try{
            if (date == 0){
                LocalDate start_date = LocalDate.of(year, month, 1);
                LocalDate end_date = start_date.plusMonths(1).minusDays(1);
                Date _start_date = Date.from(start_date.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date _end_date = Date.from(end_date.atStartOfDay(ZoneId.systemDefault()).toInstant());
                SelectArg start_dateArg = new SelectArg(_start_date);
                SelectArg end_dateArg = new SelectArg(_end_date);
                if ((employee_code == null) || (employee_code.equals(""))){
                    queryBuilder.where().between("date", start_dateArg, end_dateArg);
                }
                else {
                    queryBuilder.where().eq("employeeCode", employee_code).and().between("date", start_dateArg, end_dateArg);
                }
            }
            else {
                LocalDate current_date = LocalDate.of(year, month, date);
                Date _current_date = Date.from(current_date.atStartOfDay(ZoneId.systemDefault()).toInstant());
                SelectArg current_dateArg = new SelectArg(_current_date);
                if ((employee_code == null) || (employee_code.equals(""))){
                    queryBuilder.where().eq("date", current_dateArg);
                }
                else {
                    queryBuilder.where().eq("employeeCode", employee_code).and().eq("date", current_dateArg);
                }
            }
            var statement = queryBuilder.prepare();
            System.out.println(statement.getStatement());
            return (ArrayList<Request>) dao.query(statement);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




/*    @Override
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
    }*/

    @Override
    public void insertRequest(Request req) {
        try {
            var _date = req.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String sql = "INSERT INTO Request (employeeCode, date, hoursEarlyLeave, hoursLate, morningSession, afternoonSession, reason, status, logAttendanceId, response) VALUES " +
                    String.format("('%s', '%s', %f, %f, %d, %d, '%s', %d, %d, '%s')",
                            req.getEmployeeCode(),
                            _date,
                            req.getHoursEarlyLeave(),
                            req.getHoursLate(),
                            req.isMorningSession() ? 1 : 0,
                            req.isAfternoonSession() ? 1 : 0,
                            req.getReason(),
                            req.getStatus(),
                            req.getLogAttendanceId(),
                            req.getResponse());
            System.out.println(sql);
            var r = dao.executeRaw(sql);
            var x = r;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*public void insertRequest(Request req) {
        try {
            String sql = "INSERT INTO Request (employeeCode, date, hoursEarlyLeave, hoursLate, morningSession, afternoonSession, reason, status) VALUES ";


            String s = String.format("('%s', '%s', %f, %f, %d, %d, '%s', %d)",
                    req.getEmployeeCode(),
                    req.getDate(),
                    req.getHoursEarlyLeave(),
                    req.getHoursLate(),
                    req.isMorningSession() ? 1 : 0,
                    req.isAfternoonSession() ? 1 : 0,
                    req.getReason(),
                    req.getStatus());

            sql += String.join(",", s);

            dao.executeRaw(sql);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    public ArrayList<Request> getRequestNotification(User user, Employee employee) {

        var queryBuilder = dao.queryBuilder();
        try {
            queryBuilder.where()
                    .eq("employeeCode", employee.getEmployeeCode())
                    .and(2);
            queryBuilder.where().eq("status", 0)
                    .or()
                    .eq("status", 2);

            queryBuilder.orderBy("date", false);


            var statement = queryBuilder.prepare();

            System.out.println(statement.getStatement());

            return (ArrayList<Request>) dao.query(statement);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateRequest(User user, int id, int status, String respond){

        try {
            String sql = "UPDATE Request SET status = " + status + ", respond = '" + respond + "' WHERE id = " + id;
            System.out.println(sql);
            var r = dao.executeRaw(sql);
            var x = r;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}