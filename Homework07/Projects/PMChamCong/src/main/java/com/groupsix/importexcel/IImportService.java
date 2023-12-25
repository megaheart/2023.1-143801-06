package com.groupsix.importexcel;

import com.groupsix.attendance.OfficerAttendance;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IImportService {
    void startImport(File file) throws Exception;

    List<ImportHistory> getAllHistoryImport();
    ImportHistory getHistoryImport(int id);

    void deleteHistoryImport(int id);

    List<AttendanceLogImport> GetAttendanceLogImportFromFile(File file) throws IOException;

    List<OfficerAttendance> getOfficerAttendancesByHistoryId(int id);

}
