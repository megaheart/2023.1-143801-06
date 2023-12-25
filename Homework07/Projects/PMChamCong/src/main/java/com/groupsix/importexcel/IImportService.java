package com.groupsix.importexcel;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IImportService {
    void startImport(File file) throws Exception;

    List<ImportHistory> getAllHistoryImport();

    void deleteHistoryImport(int id);

    List<AttendanceLogImport> GetAttendanceLogImportFromFile(File file) throws IOException;

}
