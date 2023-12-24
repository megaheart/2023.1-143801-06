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

            historyImportRepository.save(importHistory);
            officerAttendanceRepository.insertMany(officerAttendances);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ImportHistory> getAllHistoryImport() {
        return historyImportRepository.getAll();
    }

    @Override
    public void deleteHistoryImport(int id) {
        ImportHistory importHistory = historyImportRepository.getById(id);
        if (importHistory == null) {
            throw new NullPointerException("Không tìm thấy lịch sử import có id: " + id);
        }
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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            sheet.forEach(row -> {
                int index = row.getRowNum();
                if (index == 0) return;
                String code = dataFormatter.formatCellValue(row.getCell(1));
                if(code.isEmpty()) return;
                Date time = row.getCell(0).getDateCellValue();
                String timestamp = simpleDateFormat.format(time);
                attendanceLogImports.add(new AttendanceLogImport(index, ExcelHelper.getFormatDate(timestamp), code));
            });
            return attendanceLogImports;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public OfficerAttendance createOfficerAttendance(Employee employee, AttendanceLogImport attendanceLogImport){
        try {
            OfficerAttendance officerAttendance = new OfficerAttendance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = simpleDateFormat.parse(attendanceLogImport.getTimestamp());

            int hour = ExcelHelper.getHour(attendanceLogImport.getTimestamp());
            int minute = ExcelHelper.getMinute(attendanceLogImport.getTimestamp());

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
            officerAttendance.setHoursLate(hoursLate);
            officerAttendance.setEmployeeCode(employee.getEmployeeCode());
//            officerAttendance.setDate(date);

            officerAttendance.setMorningSession(isMorningSession);
            officerAttendance.setAfternoonSession(isAfternoonSession);


            return officerAttendance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
