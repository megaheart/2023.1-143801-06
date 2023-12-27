package com.groupsix.user;

import com.groupsix.base.DatabaseHelper;
import com.groupsix.user.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


public class UserServiceTest {

    @BeforeAll
    public static void initAll() {
        UserFactory.getInstance().registerRepository(FakeUserRepository.class);
    }

    @ParameterizedTest
    @DisplayName("Test authenticate return true")
    @CsvSource({
            "user, 1234"
    })
    public void authenticate_TrueResult(String username, String password){
        // Given
        UserService userService = UserService.getInstance();

        // When
        boolean isSuccess = userService.authenticate(username, password);

        // Then
        assertEquals(isSuccess, true);
    }

    @ParameterizedTest
    @DisplayName("Test authenticate return false")
    @CsvSource({
            "admin, 123456",
            "user, 123456",
            "hello, 1234",
    })
    public void authenticate_FalseResult(String username, String password){
        // Given
        UserService userService = UserService.getInstance();

        // When
        boolean isSuccess = userService.authenticate(username, password);

        // Then
        assertEquals(isSuccess, false);
    }

    @ParameterizedTest
    @DisplayName("Test authenticate user not null and password same")
    @CsvSource({
            "user, 1234"
    })
    public void authenticate_UserNotNull_PasswordSame(String username, String password){
        // Given
        UserService userService = UserService.getInstance();

        // When
        var isSuccess = userService.authenticate(username, password);

        // Then
        assertEquals(isSuccess, true);
    }

    @ParameterizedTest
    @DisplayName("Test authenticate user null")
    @CsvSource({
            "admin, 123456"
    })
    public void authenticate_UserNull(String username, String password){
        // Given
        UserService userService = UserService.getInstance();

        // When
        boolean isSuccess = userService.authenticate(username, password);

        // Then
        assertEquals(isSuccess, false);
    }

    @ParameterizedTest
    @DisplayName("Test authenticate user not null and password not same")
    @CsvSource({
            "user, 123456"
    })
    public void authenticate_UserNotNull_PasswordNotSame(String username, String password){
        // Given
        UserService userService = UserService.getInstance();

        // When
        var isSuccess = userService.authenticate(username, password);

        // Then
        assertEquals(isSuccess, false);
    }


}
