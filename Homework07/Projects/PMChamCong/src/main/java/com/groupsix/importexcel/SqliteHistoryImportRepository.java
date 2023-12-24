package com.groupsix.importexcel;

import com.groupsix.base.DatabaseHelper;
import com.groupsix.user.User;
import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;
import java.util.List;

public class SqliteHistoryImportRepository implements IHistoryImportRepository {

    private final Dao<ImportHistory, Integer> dao;

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
    public ImportHistory getById(int id) {
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
    public void deleteById(int id) {
        try {
            dao.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
