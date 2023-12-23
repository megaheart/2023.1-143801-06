package com.groupsix.importexcel;

import com.groupsix.attendance.IOfficerAttendanceRepository;
import com.groupsix.attendance.OfficerAttendance;
import com.groupsix.hrsubsystem.Employee;
import com.groupsix.hrsubsystem.IEmployeeRepository;
import com.groupsix.user.User;
import com.groupsix.user.UserService;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ImportService implements IImportService {
    private final IHistoryImportRepository historyImportRepository;
    private final IOfficerAttendanceRepository officerAttendanceRepository;
    private final IEmployeeRepository employeeRepository;

//    private

    public ImportService(IHistoryImportRepository historyImportRepository, IOfficerAttendanceRepository officerAttendanceRepository, IEmployeeRepository employeeRepository) {
        this.historyImportRepository = historyImportRepository;
        this.officerAttendanceRepository = officerAttendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    public boolean checkFileValid(File file){
        if(file == null || !file.exists() || !file.getName().endsWith(".xlsx")) return false;
        return true;
    }

    public void startImport(File file)  {
        try {
            List<AttendanceLogImport> attendanceLogImports = GetAttendanceLogImportFromFile(file);
            if (attendanceLogImports.size() == 0) {
                throw new Exception("File không có dữ liệu");
            }
            List<String> codes = Set.copyOf(attendanceLogImports
                                    .stream()
                                    .map(AttendanceLogImport::getEmployeeCode)
                                    .toList()).stream().toList();

            List<Employee> employees = getListEmployees(codes);

            List<OfficerAttendance> officerAttendances = new ArrayList<>();
            attendanceLogImports.forEach(attendanceLogImport -> {
                Employee employee = employees.stream().filter(e -> e.getEmployeeCode().equals(attendanceLogImport.getEmployeeCode())).findFirst().orElse(null);
                if (employee != null) {
                    OfficerAttendance officerAttendance = createOfficerAttendance(employee, attendanceLogImport);
                    officerAttendances.add(officerAttendance);
                }
            });

            ImportHistory importHistory = new ImportHistory();
            importHistory.setCreatedBy("ADMIN");
            importHistory.setTime(Calendar.getInstance().getTime().toString());

            officerAttendanceRepository.insertMany(officerAttendances);
            historyImportRepository.save(importHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ImportHistory> getAllHistoryImport() {
        return historyImportRepository.getAll();
    }

    @Override
    public void deleteHistoryImport(String id) {
        historyImportRepository.deleteById(id);
    }

    public List<AttendanceLogImport> GetAttendanceLogImportFromFile(File file){
        try {
            if (!checkFileValid(file)) return null;
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            List<AttendanceLogImport> attendanceLogImports = new ArrayList<>();
            // Read each row, skip first row
            sheet.forEach(row -> {
                int index = row.getRowNum();
                if (index == 0) return;
                String time = dataFormatter.formatCellValue(row.getCell(0));
                String code = dataFormatter.formatCellValue(row.getCell(1));
                attendanceLogImports.add(new AttendanceLogImport(index, time, code));
            });
            return attendanceLogImports;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Employee> getListEmployees(List<String> codes){
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkDuplicateCodes(List<String> codes){
        try {
            Set<String> codesSet = Set.copyOf(codes);
            if (codesSet.size() != codes.size()) {
                throw new Exception("Danh sách mã nhân viên có mã lặp lại");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public OfficerAttendance createOfficerAttendance(Employee employee, AttendanceLogImport attendanceLogImport){
        try {
            OfficerAttendance officerAttendance = new OfficerAttendance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = simpleDateFormat.parse(attendanceLogImport.getTimestamp());

            boolean isMorningSession = false;
            boolean isAfternoonSession = false;
            int hoursLate = 0;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);

            if (hour < 12) {
                isMorningSession = true;
                int minute = calendar.get(Calendar.MINUTE);
                if (hour >= 8){
                    hoursLate = (hour - 8) + minute/60;
                    officerAttendance.setMorningHoursLate((float) Math.round(hoursLate * 100) / 100);
                }
            } else {
                isAfternoonSession = true;
                int minute = calendar.get(Calendar.MINUTE);
                if (hour >= 14){
                    hoursLate = (hour - 8) + minute/60;
                    officerAttendance.setMorningHoursLate((float) Math.round(hoursLate * 100) / 100);
                }
            }
            officerAttendance.setEmployeeCode(employee.getEmployeeCode());
            officerAttendance.setDate(date);

            officerAttendance.setMorningSession(isMorningSession);
            officerAttendance.setAfternoonSession(isAfternoonSession);

            if(isMorningSession){
                officerAttendance.setMorningHoursLate(hoursLate);
            } else{
                officerAttendance.setAfternoonHoursLate(hoursLate);
            }
            return officerAttendance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
