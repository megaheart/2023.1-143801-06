package com.groupsix.importexcel;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.attendance.IOfficerAttendanceRepository;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.hrsubsystem.Employee;
import com.groupsix.hrsubsystem.HRSubsystemFactory;
import com.groupsix.hrsubsystem.IEmployeeRepository;
import com.groupsix.pages.importexcel.Session;
import com.groupsix.user.UserService;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ImportService implements IImportService {
    private final IHistoryImportRepository historyImportRepository;
    private final IOfficerAttendanceRepository officerAttendanceRepository;
    private final IEmployeeRepository employeeRepository;

    public ImportService() {
        this.historyImportRepository = ImportHistoryFactory.getInstance().createRepository();
        this.officerAttendanceRepository = AttendanceFactory.getInstance().createRepository();
        this.employeeRepository = HRSubsystemFactory.getInstance().createEmployeeRepository();
    }

    public boolean checkFileValid(File file){
        if(file == null || !file.exists() || !file.getName().endsWith(".xlsx")) return false;
        return true;
    }

    public void importOfficerAttendance(File file) throws Exception {
            List<AttendanceLogImport> attendanceLogImports = GetAttendanceLogImportFromFile(file);
            if (attendanceLogImports.size() == 0) {
                throw new Exception("File không có dữ liệu");
            }
            List<String> codes = Set.copyOf(attendanceLogImports
                                    .stream()
                                    .map(AttendanceLogImport::getEmployeeCode)
                                    .toList()).stream().toList();

            List<Employee> employees = getListEmployees(codes);

            ImportHistory importHistory = new ImportHistory();
            importHistory.setCreatedBy(UserService.getInstance().getCurrentUser().getEmployeeCode());
            importHistory.setTime(Calendar.getInstance().getTime().toString());

            int idReturn = historyImportRepository.save(importHistory);
            List<OfficerAttendance> officerAttendances = new ArrayList<>();
            attendanceLogImports.forEach(attendanceLogImport -> {
                Employee employee = employees.stream()
                        .filter(e -> e.getEmployeeCode().equals(attendanceLogImport.getEmployeeCode()))
                        .findFirst().orElse(null);
                if (employee != null) {
                    OfficerAttendance officerAttendance;
                    try {
                        officerAttendance = createOfficerAttendance(employee, attendanceLogImport);
                        officerAttendance.setHistoryImportId(idReturn);
                        officerAttendances.add(officerAttendance);
                     } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            officerAttendanceRepository.insertMany(officerAttendances);
    }

    public void importOfficerAttendance(List<OfficerAttendance> officerAttendances) {
        officerAttendanceRepository.insertMany(officerAttendances);
    }

    @Override
    public List<ImportHistory> getAllHistoryImport() {
        return historyImportRepository.getAll();
    }

    @Override
    public ImportHistory getHistoryImport(int id) {
        return historyImportRepository.getById(id);
    }

    @Override
    public void deleteHistoryImport(int id) {
        ImportHistory importHistory = historyImportRepository.getById(id);
        if (importHistory == null) {
            throw new NullPointerException("Không tìm thấy lịch sử import có id: " + id);
        }
        historyImportRepository.deleteById(id);
    }

    public List<AttendanceLogImport> GetAttendanceLogImportFromFile(File file) throws Exception {
        try {
            if (!checkFileValid(file)) return null;
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            List<AttendanceLogImport> attendanceLogImports = new ArrayList<>();
            // Read each row, skip first row
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            sheet.forEach(row -> {
                int index = row.getRowNum();
                if (index == 0) return;
                String code = dataFormatter.formatCellValue(row.getCell(1));
                if (code.isEmpty()) return;
                String time = dataFormatter.formatCellValue(row.getCell(0));
//                String timestamp = simpleDateFormat.format(time);
                try {
                    attendanceLogImports.add(new AttendanceLogImport(index, ExcelHelper.getFormatDate(time), code));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return attendanceLogImports;
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Không tìm thấy file");
        }
        catch (IOException e) {
            throw new IOException("Lỗi đọc file");
        }
        catch (Exception e) {
            throw new Exception("Lỗi đọc file");
        }
    }

    public List<Employee> getListEmployees(List<String> codes) throws Exception {
            List<Employee> employees = employeeRepository.getEmployeesByListCodes(codes);
            List<String> codesFound = employees.stream().map(Employee::getEmployeeCode).toList();
            List<String> codesNotFound = new ArrayList<>();
            codes.forEach(code -> {
                if (!codesFound.contains(code)) codesNotFound.add(code);
            });
            if (codesNotFound.size() > 0) {
                throw new Exception("Không tìm thấy nhân viên có mã: " + String.join(", ", codesNotFound));
            }
            return employees;
    }

    public Session createSession(String timestamp) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = simpleDateFormat.parse(timestamp);
        int hour = date.getHours();
        int minute = date.getMinutes();

        boolean isMorningSession = false;
        boolean isAfternoonSession = false;
        double hoursLate = 0;
        if (hour < 12) {
            isMorningSession = true;
            if (hour >= 8){
                hoursLate = (hour - 8) + (double) minute/60;
            }
        } else {
            isAfternoonSession = true;
            if (hour >= 14){
                hoursLate = (hour - 8) + (double) minute/60;
            }
        }
        Session session = new Session();
        session.time = date;
        session.isAfternoonSession = isAfternoonSession;
        session.isMorningSession = isMorningSession;
        session.hoursLate = hoursLate;

        return session;
    }

    public OfficerAttendance createOfficerAttendance(Employee employee, AttendanceLogImport attendanceLogImport)
            throws ParseException {
            OfficerAttendance officerAttendance = new OfficerAttendance();
            Session session = createSession(attendanceLogImport.getTimestamp());
            officerAttendance.setHoursLate(session.hoursLate);
            officerAttendance.setEmployeeCode(employee.getEmployeeCode());
            officerAttendance.setDate(session.time);

            officerAttendance.setMorningSession(session.isMorningSession);
            officerAttendance.setAfternoonSession(session.isAfternoonSession);

            return officerAttendance;
    }

    public List<OfficerAttendance> getOfficerAttendancesByHistoryId(int id) {
        List<OfficerAttendance> officerAttendances = officerAttendanceRepository.getAttendancesByHistoryId(id);
        return officerAttendances;
    }
}
