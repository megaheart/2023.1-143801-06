package com.groupsix.importexcel;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.attendance.IOfficerAttendanceRepository;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.attendance.SqliteOfficerAttendanceRepository;
import com.groupsix.base.DatabaseHelper;
import com.groupsix.hrsubsystem.*;
import com.groupsix.user.SqliteUserRepository;
import com.groupsix.user.User;
import com.groupsix.user.UserFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

class ImportServiceTest {

    @Mock
    private IHistoryImportRepository historyImportRepository;

    @Mock
    private IOfficerAttendanceRepository officerAttendanceRepository;

    @Mock
    private IEmployeeRepository employeeRepository;

    private ImportService importService;

    List<ImportHistory> importHistories;

    ArrayList<Employee> employees;

    @BeforeEach
    void setUp() {
        DatabaseHelper.EnsureTableExist(ImportHistory.class);
        DatabaseHelper.EnsureTableExist(OfficerAttendance.class);
        DatabaseHelper.EnsureTableExist(Employee.class);

        ImportHistoryFactory.getInstance().registerRepository(SqliteHistoryImportRepository.class);
        AttendanceFactory.getInstance().registerRepository(SqliteOfficerAttendanceRepository.class);
        HRSubsystemFactory.getInstance().registerEmployeeRepository(EmployeeAdapter.class);

        importHistories = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            importHistories.add(
                    new ImportHistory(i, "20/10/2021 10:20:33", "ADMIN")
            );
        }

        employees = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Employee employee = new Employee();
            employee.setEmployeeCode("EMP00" + i);
            employees.add(employee);
        }

        historyImportRepository = Mockito.mock(IHistoryImportRepository.class);
        when(historyImportRepository.getAll()).thenReturn(importHistories);
        doAnswer(
                invocation -> {
                    int idDelete = invocation.getArgument(0);
                    importHistories.removeIf(importHistory -> importHistory.getId() == idDelete);
                    return null;
                }
        ).when(historyImportRepository).deleteById(Mockito.anyInt());
        doAnswer(
                invocation -> {
                    int id = invocation.getArgument(0);
                    return importHistories.stream().filter(importHistory -> importHistory.getId() == id).findFirst().orElse(null);
                }
        ).when(historyImportRepository).getById(Mockito.anyInt());

        officerAttendanceRepository = Mockito.mock(IOfficerAttendanceRepository.class);

        employeeRepository = Mockito.mock(IEmployeeRepository.class);
        when(employeeRepository.getEmployees()).thenReturn(employees);

        importService = new ImportService(historyImportRepository, officerAttendanceRepository, employeeRepository);
    }

    @Test
    @DisplayName("Check file valid success")
    void checkFileValid_Success() {
        // Given
        String path = "C://Users/HP PAVILION/Documents/testImport.xlsx";

        // When
        File file = new File(path);
        boolean result = importService.checkFileValid(file);
        // Then
        assertTrue(result);
    }

    @ParameterizedTest
    @DisplayName("Check file valid - fail")
    @ValueSource(strings = {"C://Users/HP PAVILION/Documents/tesmort.xlsx",
            "C://Users/HP PAVILION/Documents/Toan.docx"})
    void checkFileValid_FailNull(String url) {
        // Given
        // When
        File file = new File(url);

        boolean result = importService.checkFileValid(file);
        // Then
        assertFalse(result);
    }

    @Test
    void startImport() {
    }

    @Test
    void getAllHistoryImport() {
        // Given
        // When
        List<ImportHistory> listImport = importService.getAllHistoryImport();
        // Then
        assertEquals(listImport, importHistories);
    }

    @ParameterizedTest
    @DisplayName("Delete history import - success")
    @ValueSource(ints = {1})
    void deleteHistoryImport_Success(int id) {
        // Given
        // When

        // Then
        assertDoesNotThrow(() -> {
            importService.deleteHistoryImport(id);
            boolean isExist = importHistories.stream().anyMatch(importHistory -> importHistory.getId() == id);
            assertFalse(isExist);
        });
    }

    @ParameterizedTest
    @DisplayName("Delete history import - success")
    @ValueSource(ints = {1})
    void getHistoryImport_Success(int id) {
        // Given
        // When
        // Then
        assertDoesNotThrow(() -> {
            ImportHistory history = historyImportRepository.getById(id);
            assertNotNull(history);
        });
    }

    @ParameterizedTest
    @DisplayName("Delete history import - fail - ID not exist")
    @ValueSource(ints = {11})
    void deleteHistoryImport_Fail(int id) {
        // Given
        // When
        // Then
        assertThrows(NullPointerException.class, () -> {
            importService.deleteHistoryImport(id);
        });
    }

    @Test
    void getAttendanceLogImportFromFile() {
    }

    static public Stream<Arguments> getListEmployees_Success(){
        return Stream.of(
                Arguments.of(Arrays.asList("EMP001", "EMP002", "EMP003")),
                Arguments.of(Arrays.asList("EMP004", "EMP005")),
                Arguments.of(Arrays.asList("EMP008", "EMP009"))
        );
    }

    @DisplayName("Get list employees - success")
    @ParameterizedTest
    @MethodSource
    void getListEmployees_Success(List<String> codes) throws Exception {
        // Given
        List<Employee> actual = employees.stream()
                .filter(employee -> codes.contains(employee.getEmployeeCode()))
                .toList();
        when(employeeRepository.getEmployeesByListCodes(codes)).thenReturn(actual);
        // When
        List<Employee> listGet = importService.getListEmployees(codes);
        // Then
        assertEquals(listGet, actual);
    }

    static public Stream<Arguments> getListEmployees_Fail(){
        return Stream.of(
                Arguments.of(Arrays.asList("EMP001", "EMP002", "EMP0010")),
                Arguments.of(Arrays.asList("EMP0011", "EMP005")),
                Arguments.of(Arrays.asList("EMP0077", "EMP009"))
        );
    }

    @DisplayName("Get list employees - fail")
    @ParameterizedTest
    @MethodSource
    void getListEmployees_Fail(List<String> codes) {
        // Given
        List<Employee> actual = employees.stream()
                .filter(employee -> codes.contains(employee.getEmployeeCode()))
                .toList();
        when(employeeRepository.getEmployeesByListCodes(codes)).thenReturn(actual);
        // When
        // Then
        assertThrows(Exception.class, () -> {
            importService.getListEmployees(codes);
        });
    }

    @Test
    void createOfficerAttendance() {
    }
}