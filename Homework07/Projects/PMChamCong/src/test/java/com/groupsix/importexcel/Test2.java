package com.groupsix.importexcel;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.attendance.SqliteOfficerAttendanceRepository;
import com.groupsix.base.DatabaseHelper;
import com.groupsix.hrsubsystem.*;
import com.groupsix.pages.importexcel.Session;
import com.groupsix.request.Request;
import com.groupsix.request.RequestFactory;
import com.groupsix.request.SqliteRequestRepository;
import com.groupsix.user.SqliteUserRepository;
import com.groupsix.user.User;
import com.groupsix.user.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.ParseException;

public class Test2 {
    private ImportService importService;

    @BeforeEach
    void setUp(){
        DatabaseHelper.EnsureTableExist(Employee.class);
        DatabaseHelper.EnsureTableExist(Department.class);
        DatabaseHelper.EnsureTableExist(User.class);
        DatabaseHelper.EnsureTableExist(OfficerAttendance.class);
        DatabaseHelper.EnsureTableExist(ImportHistory.class);
        DatabaseHelper.EnsureTableExist(Request.class);
        HRSubsystemFactory.getInstance().registerEmployeeRepository(EmployeeAdapter.class);
        HRSubsystemFactory.getInstance().registerDepartmentRepository(DepartmentAdapter.class);
        UserFactory.getInstance().registerRepository(SqliteUserRepository.class);
        AttendanceFactory.getInstance().registerRepository(SqliteOfficerAttendanceRepository.class);
        ImportHistoryFactory.getInstance().registerRepository(SqliteHistoryImportRepository.class);
        ImportServiceFactory.getInstance().registerRepository(ImportService.class);
        RequestFactory.getInstance().registerRepository(SqliteRequestRepository.class);
        importService = new ImportService();
    }

    @ParameterizedTest
    @ValueSource(strings = {"20/10/2022 09:10:33", "20/11/2022 09:10:33"})
    void createSession_SuccessMorning(String dateString) throws ParseException {
        Assertions.assertDoesNotThrow(()->{
            Session session = importService.createSession(dateString);
            Assertions.assertTrue(session.isMorningSession);
            Assertions.assertTrue(!session.isAfternoonSession);
        });
    }
    @ParameterizedTest
    @ValueSource(strings = {"20/10/2023 14:10:33", "20/11/2023 13:10:33"})
    void createSession_SuccessAfternoon(String dateString) throws ParseException {
        Assertions.assertDoesNotThrow(()->{
            Session session = importService.createSession(dateString);
            Assertions.assertTrue(!session.isMorningSession);
            Assertions.assertTrue(session.isAfternoonSession);
        });
    }
    @ParameterizedTest
    @ValueSource(strings = {"20/0/2023 14:10:33", "20/11/203 13:10:33"})
    void createSession_FailA(String dateString) throws ParseException {
        Assertions.assertThrows(Exception.class,()->{
            Session session = importService.createSession(dateString);
        });
    }
}
