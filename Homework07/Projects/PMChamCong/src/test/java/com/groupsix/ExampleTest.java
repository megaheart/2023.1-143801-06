package com.groupsix;

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
import static org.mockito.Mockito.when;


public class ExampleTest {

    @BeforeAll
    public static void initAll() {
        DatabaseHelper.EnsureTableExist(User.class);

        UserFactory.getInstance().registerRepository(SqliteUserRepository.class);
    }

    @ParameterizedTest
    @DisplayName("Test authenticate fail")
    @CsvSource({
            "admin, 123456",
            "user, 123456"
    })
    public void testAuthenticateFail(String username, String password){
        // Given

        // When
        UserService userService = UserService.getInstance();
        boolean isSuccess = userService.authenticate(username, password);

        // Then
        assertEquals(isSuccess, false );
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

    @Test
    public void testSubTwoNumber(){
        final int expected = 1;
        final int actual = 2 - 1;
        assertEquals(expected, actual);
    }

    @Test
    public void testAddTwoNumber(){
        final int expected = 2;
        final int actual = 1 + 1;
        assertEquals(expected, actual);
    }

}
