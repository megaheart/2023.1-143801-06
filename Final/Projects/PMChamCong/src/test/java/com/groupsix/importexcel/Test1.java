package com.groupsix.importexcel;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.attendance.SqliteOfficerAttendanceRepository;
import com.groupsix.base.DatabaseHelper;
import com.groupsix.hrsubsystem.*;
import com.groupsix.request.Request;
import com.groupsix.request.RequestFactory;
import com.groupsix.request.SqliteRequestRepository;
import com.groupsix.user.SqliteUserRepository;
import com.groupsix.user.User;
import com.groupsix.user.UserFactory;
import com.groupsix.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

public class Test1 {
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
    @Test
    void ImportDataExcel_Success(){
        String path = "C://Users/HP PAVILION/Downloads/test1.xlsx";
        UserService.getInstance().authenticate("hr","1234");
        try {
            Assertions.assertDoesNotThrow( () -> importService.importOfficerAttendance(new File(path)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void ImportDataExcel_InvalidData(){
        String path = "C://Users/HP PAVILION/Downloads/test2.xlsx";
        try {
            Assertions.assertThrows(Exception.class, () -> importService.importOfficerAttendance(new File(path)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
