package com.groupsix.importexcel;

import com.groupsix.base.DatabaseHelper;
import com.groupsix.user.User;
import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;
import java.util.List;

public class SqliteHistoryImportRepository implements IHistoryImportRepository {

    private final Dao<ImportHistory, String> dao;

    public SqliteHistoryImportRepository() {
        dao = DatabaseHelper.createDAO(ImportHistory.class);
    }


    @Override
    public void save(ImportHistory history) {
        try {
            dao.createOrUpdate(history);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ImportHistory getById(String id) {
       try {
           return dao.queryForId(id);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    public List<ImportHistory> getAll() {
        try {
            return dao.queryForAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            dao.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
