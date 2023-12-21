package com.groupsix.base;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper {
    public static final String SQLiteConnection = "jdbc:sqlite:src/main/resources/database.db";
    public static <T, ID> Dao<T, ID> createDAO(Class<T> modelClass) {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(Resources.SQLiteConnection);
            return DaoManager.createDao(connectionSource, modelClass);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T, ID> void EnsureTableExist(Class<T> modelClass) {
        Dao<T, ID> dao = createDAO(modelClass);
        try {
            if(dao.isTableExists()) {
                return;
            }
            TableUtils.createTable(dao);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
