package com.groupsix.user;

import com.groupsix.base.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


class SqliteUserRepositoryTest {

    @BeforeAll
    static void setUp() {
        DatabaseHelper.EnsureTableExist(User.class);
        UserFactory.getInstance().registerRepository(SqliteUserRepository.class);
    }

    @Test
    @DisplayName("Test get all users")
    void getUsers_ReturnSuccess() throws SQLException {
        List<User> users = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            User user = new User();
            user.setUsername("username" + i);
            user.setPassword("password" + i);
            user.setRole("role" + i);
            user.setDepartmentCode("departmentCode" + i);
            user.setEmployeeCode("employeeCode" + i);
            users.add(user);
        }

        Dao<User, String> dao = Mockito.mock(Dao.class);
        when(dao.queryForAll()).thenReturn(users);

        try(MockedStatic<DatabaseHelper> mockedStatic = Mockito.mockStatic(DatabaseHelper.class)){
            mockedStatic.when((MockedStatic.Verification) DatabaseHelper.createDAO(User.class)).thenReturn(dao);
            SqliteUserRepository sqliteUserRepository = new SqliteUserRepository();

            assertEquals(sqliteUserRepository.getUsers(), users);
        }
    }

    @Test
    @DisplayName("Test get all users return null")
    void getUsers_ReturnNull() throws SQLException {
        Dao<User, String> dao = Mockito.mock(Dao.class);
        when(dao.queryForAll()).thenReturn(null);

        try(MockedStatic<DatabaseHelper> mockedStatic = Mockito.mockStatic(DatabaseHelper.class)){
            mockedStatic.when((MockedStatic.Verification) DatabaseHelper.createDAO(User.class)).thenReturn(dao);
            SqliteUserRepository sqliteUserRepository = new SqliteUserRepository();

            assertEquals(sqliteUserRepository.getUsers(), null);
        }
    }

    @ParameterizedTest
    @DisplayName("Test get user by username return success")
    @ValueSource(strings = {"username_success"})
    void getUserByUsername(String username) throws SQLException {
        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        Dao<User, String> dao = Mockito.mock(Dao.class);
        when(dao.queryForId(username)).thenReturn(user);

        try(MockedStatic<DatabaseHelper> mockedStatic = Mockito.mockStatic(DatabaseHelper.class)){
            mockedStatic.when((MockedStatic.Verification) DatabaseHelper.createDAO(User.class)).thenReturn(dao);
            SqliteUserRepository sqliteUserRepository = new SqliteUserRepository();

            User result = sqliteUserRepository.getUserByUsername(username);
            assertNotNull(result);
            assertEquals(result.getUsername(), username);
            assertEquals(result.getPassword(), user.getPassword());
        }
    }

    @ParameterizedTest
    @DisplayName("Test get user by username return null")
    @ValueSource(strings = {"username_fail"})
    void getUserByUsername_ReturnNull(String username) throws SQLException {
        Dao<User, String> dao = Mockito.mock(Dao.class);
        when(dao.queryForId(username)).thenReturn(null);

        try(MockedStatic<DatabaseHelper> mockedStatic = Mockito.mockStatic(DatabaseHelper.class)){
            mockedStatic.when((MockedStatic.Verification) DatabaseHelper.createDAO(User.class)).thenReturn(dao);
            SqliteUserRepository sqliteUserRepository = new SqliteUserRepository();

            User result = sqliteUserRepository.getUserByUsername(username);
            assertNull(result);
        }
    }
}