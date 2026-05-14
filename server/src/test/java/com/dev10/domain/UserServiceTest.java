package com.dev10.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.dev10.models.DataAccessException;
import com.dev10.models.DTO.Result;
import com.dev10.models.User;
import com.dev10.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static com.dev10.TestDataHelper.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {
    @MockitoBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    void createAccountHappyPath() throws DataAccessException {
        User notInDatabase = getUserNotInDatabase();
        notInDatabase.setSalt("test");
        User updatedUser = getUserNotInDatabase();
        updatedUser.setId(3);
        when(userRepository.createUser(notInDatabase)).thenReturn(updatedUser);

        Result<User> result = userService.createAccount(notInDatabase);

        assertTrue(result.isSuccess());
        assertEquals(updatedUser, result.getPayload());
        verify(userRepository).createUser(notInDatabase);
    }

    @Test
    void createAccountFailsIfUserIsNull() throws DataAccessException {
        Result<User> result = userService.createAccount(null);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        verify(userRepository, never()).createUser(any());
    }

    @Test
    void createAccountRejectsIfIdIsAlreadySet() throws DataAccessException {
        User user = getUserNotInDatabase();
        user.setId(3);

        failUserCreation(user);
    }

    @Test
    void createAccountRejectsIfEmailAlreadyExists() throws DataAccessException {
        User duplicateUser = getAllUsers().get(0);

        failUserCreation(duplicateUser);
    }

    @Test
    void createAccountFailsIfEmailIsNullEmptyOrWhitespace() throws DataAccessException {
        User nullEmail = getUserNotInDatabase();
        nullEmail.setEmail(null);

        User emptyEmail = getUserNotInDatabase();
        emptyEmail.setEmail("");

        User blankEmail = getUserNotInDatabase();
        blankEmail.setEmail("  ");

        failUserCreation(nullEmail);
        failUserCreation(emptyEmail);
        failUserCreation(blankEmail);
    }

    @Test
    void createAccountFailsIfPasswordIsNullEmptyOrWhitespace() throws DataAccessException {
        User nullPassword = getUserNotInDatabase();
        nullPassword.setPassword(null);

        User emptyPassword = getUserNotInDatabase();
        emptyPassword.setPassword("");

        User blankPassword = getUserNotInDatabase();
        blankPassword.setPassword("  ");

        failUserCreation(nullPassword);
        failUserCreation(emptyPassword);
        failUserCreation(blankPassword);
    }

    public void failUserCreation(User user) throws DataAccessException {
        Result<User> result = userService.createAccount(user);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        verify(userRepository, never()).createUser(any());
    }
}
