package com.groupsix.importexcel;

import java.io.File;
import java.util.List;

public interface IImportService {
    void startImport(File file);

    List<ImportHistory> getAllHistoryImport();

    void deleteHistoryImport(int id);

}
