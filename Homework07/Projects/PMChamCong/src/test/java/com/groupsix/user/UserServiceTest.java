package com.groupsix.user;

import com.groupsix.base.DatabaseHelper;
import com.groupsix.user.*;
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
        DatabaseHelper.EnsureTableExist(User.class);

        UserFactory.getInstance().registerRepository(SqliteUserRepository.class);
    }

    @Test
    @DisplayName("Test get instance")
    public void testGetInstance(){
        // Given
        UserService userService = UserService.getInstance();

        // Then
        assertNotNull(userService);
    }

    @Test
    @DisplayName("Test logout")
    public void testLogout(){
        // Given
        UserService userService = UserService.getInstance();
        userService.logout();

        // Then
        assertEquals(userService.getCurrentUser(), null);
    }

    @ParameterizedTest
    @DisplayName("Test authenticate fail")
    @CsvSource({
            "admin, 123456",
            "user, 123456"
    })
    public void testAuthenticateFail(String username, String password){
        // Given

        // Mock repository
        IUserRepository mockUserRepository = Mockito.mock(IUserRepository.class);
        // Mock method getUserByUsername return null
        when(mockUserRepository.getUserByUsername(username)).thenReturn(null);
        // Mock factory createRepository return mock repository
        UserFactory mockUserFactory = Mockito.mock(UserFactory.class);
        // Mock method createRepository return mock repository
        when(mockUserFactory.createRepository()).thenReturn(mockUserRepository);

        // Mock static method
        try(MockedStatic<UserFactory> mockUserFactoryStatic = Mockito.mockStatic(UserFactory.class)){
            mockUserFactoryStatic.when(UserFactory::getInstance).thenReturn(mockUserFactory);

            // When
            UserService userService = UserService.getInstance();
            boolean isSuccess = userService.authenticate(username, password);

            // Then
            assertEquals(isSuccess, false );
        }
    }

    @ParameterizedTest
    @DisplayName("Test authenticate success")
    @CsvSource({
            "admin, 1234567",
            "user, 1234567"
    })
    public void testAuthenticateSuccess(String username, String password){
        // Given
        IUserRepository mockUserRepository = Mockito.mock(IUserRepository.class);
        when(mockUserRepository.getUserByUsername(username)).thenReturn(new User(){
            {
                setUsername(username);
                setPassword(password);
            }
        });
        UserFactory mockUserFactory = Mockito.mock(UserFactory.class);
        when(mockUserFactory.createRepository()).thenReturn(mockUserRepository);

        try(MockedStatic<UserFactory> mockUserFactoryStatic = Mockito.mockStatic(UserFactory.class)){
            mockUserFactoryStatic.when(UserFactory::getInstance).thenReturn(mockUserFactory);

            // When
            UserService userService = UserService.getInstance();
            boolean isSuccess = userService.authenticate(username, password);

            // Then
            assertEquals(isSuccess, true );
        }
    }
}
