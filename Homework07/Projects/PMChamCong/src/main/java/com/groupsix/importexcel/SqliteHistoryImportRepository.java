package com.groupsix.importexcel;

import com.groupsix.base.DatabaseHelper;
import com.groupsix.user.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

import java.util.ArrayList;
import java.util.List;

public class SqliteHistoryImportRepository implements IHistoryImportRepository {

    private final Dao<ImportHistory, Integer> dao;

    public SqliteHistoryImportRepository() {
        dao = DatabaseHelper.createDAO(ImportHistory.class);
    }


    @Override
    public int save(ImportHistory history) {
        try {
//            String sql = String.format("INSERT INTO HistoryImport (time, createdBy) VALUES ('%s', '%s')",
//                    history.getTime(), history.getCreatedBy());
//            dao.executeRaw(sql);
            var resulta = dao.queryRaw("SELECT MAX(id) FROM HistoryImport");
            String s = resulta.getFirstResult()[0];
            int newId = Integer.parseInt(s) + 1;

            history.setId(newId);
            dao.create(history);
            return newId;

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
