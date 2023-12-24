package com.groupsix.importexcel;

import java.util.List;

public interface IHistoryImportRepository {
    void save(ImportHistory history);

    ImportHistory getById(int id);

    List<ImportHistory> getAll();

    void deleteById(int id);

}
