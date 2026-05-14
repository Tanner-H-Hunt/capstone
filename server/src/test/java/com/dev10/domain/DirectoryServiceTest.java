package com.dev10.domain;

import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.Directory;
import com.dev10.models.User;
import com.dev10.repositories.DirectoryRepository;
import com.dev10.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static com.dev10.TestDataHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DirectoryServiceTest {

    @MockitoBean
    DirectoryRepository directoryRepository;

    @MockitoBean
    UserRepository userRepository;

    @Autowired
    DirectoryService service;

    @Test
    void createDirectoryHappyPath() throws DataAccessException {
        Directory directory = getDirectoryNotInDatabase();
        Directory expected = getDirectoryNotInDatabase();
        expected.setId(5);
        when(directoryRepository.createDirectory(directory)).thenReturn(expected);
        when(userRepository.findById(directory.getAccountId())).thenReturn(getAllUsers().get(0));
        when(directoryRepository.getDirectoryById(1)).thenReturn(getDirectoriesForUser1().get(0));

        Result<Directory> actual = service.createDirectory(directory);

        assertEquals(expected, actual.getPayload());
        assertTrue(actual.isSuccess());
    }

    @Test
    void createDirectoryFailsIfDirectoryIsNull() throws DataAccessException {
        Result<Directory> actual = service.createDirectory(null);

        assertFalse(actual.isSuccess());
        verify(directoryRepository, never()).createDirectory(any());
    }

    @Test
    void createDirectoryFailsIfIdWasModified() throws DataAccessException {
        Directory modifiedId = getDirectoryNotInDatabase();
        modifiedId.setId(12);

        // eliminate other errors in this test
        when(userRepository.findById(modifiedId.getAccountId())).thenReturn(getAllUsers().get(0));
        when(directoryRepository.getDirectoryById(1)).thenReturn(getDirectoriesForUser1().get(0));

        Result<Directory> actual = service.createDirectory(modifiedId);

        assertFalse(actual.isSuccess());
        verify(directoryRepository, never()).createDirectory(any());
    }

    @Test
    void createDirectoryFailsToCreateNewRootIfMultipleRootDirectoriesExist() throws DataAccessException {
        Directory secondRoot = getDirectoryNotInDatabase();
        secondRoot.setParentDirectoryId(0);

        when(userRepository.findById(secondRoot.getAccountId())).thenReturn(getAllUsers().get(0));
        when(directoryRepository.getRootDirectories(any())).thenReturn(List.of(getDirectoriesForUser1().get(0)));

        Result<Directory> actual = service.createDirectory(secondRoot);

        assertFalse(actual.isSuccess());
        verify(directoryRepository, never()).createDirectory(any());
    }

    @Test
    void createDirectoryFailsIfParentDirectoryDoesntExist() throws DataAccessException {
        Directory illegalParent = getDirectoryNotInDatabase();
        illegalParent.setParentDirectoryId(10000);

        when(userRepository.findById(illegalParent.getAccountId())).thenReturn(getAllUsers().get(0));
        when(directoryRepository.getDirectoryById(1)).thenReturn(null);

        Result<Directory> result = service.createDirectory(illegalParent);

        assertFalse(result.isSuccess());
        verify(directoryRepository, never()).createDirectory(any());
    }

    @Test
    void createDirectoryFailsIfAccountDoesNotExist() throws DataAccessException {
        User illegalUser = getUserNotInDatabase();
        Directory directory = getDirectoryNotInDatabase();
        directory.setAccountId(illegalUser.getId());

        when(userRepository.findById(illegalUser.getId())).thenReturn(null);
        when(directoryRepository.getDirectoryById(directory.getParentDirectoryId())).thenReturn(new Directory());

        Result<Directory> result = service.createDirectory(directory);

        assertFalse(result.isSuccess());
        verify(directoryRepository, never()).createDirectory(any());
    }

    @Test
    void createDirectoryFailsIfDirectoryNameIsEmpty() throws DataAccessException {
        Directory directory = getDirectoryNotInDatabase();
        directory.setDirectoryName("");

        when(userRepository.findById(directory.getAccountId())).thenReturn(new User());
        when(directoryRepository.getDirectoryById(directory.getParentDirectoryId())).thenReturn(new Directory());

        Result<Directory> result = service.createDirectory(directory);

        assertFalse(result.isSuccess());
        verify(directoryRepository, never()).createDirectory(any());
    }

    @Test
    void createDirectoryFailsIfDirectoryNameIsBlank() throws DataAccessException {
        Directory directory = getDirectoryNotInDatabase();
        directory.setDirectoryName(" ");

        when(userRepository.findById(directory.getAccountId())).thenReturn(new User());
        when(directoryRepository.getDirectoryById(directory.getParentDirectoryId())).thenReturn(new Directory());

        Result<Directory> result = service.createDirectory(directory);

        assertFalse(result.isSuccess());
        verify(directoryRepository, never()).createDirectory(any());
    }

    @Test
    void createDirectoryFailsIfDirectoryNameIsNull() throws DataAccessException {
        Directory directory = getDirectoryNotInDatabase();
        directory.setDirectoryName(null);

        when(userRepository.findById(directory.getAccountId())).thenReturn(new User());
        when(directoryRepository.getDirectoryById(directory.getParentDirectoryId())).thenReturn(new Directory());

        Result<Directory> result = service.createDirectory(directory);

        assertFalse(result.isSuccess());
        verify(directoryRepository, never()).createDirectory(any());
    }

}