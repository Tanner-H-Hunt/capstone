package com.dev10.repositories;

import com.dev10.domain.DirectoryService;
import com.dev10.models.DataAccessException;
import com.dev10.models.Directory;
import com.dev10.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import static com.dev10.TestDataHelper.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DirectoryJdbcClientRepositoryTest {
    @Autowired
    DirectoryJdbcClientRepository repository;

    @Autowired
    JdbcClient client;

    @BeforeEach
    void init(){
        client.sql("CALL set_known_good_state();").update();
    }

    @Test
    void getRootDirectoriesSucceeds(){
        List<Directory> expected = getDirectoriesForUser1()
                .stream()
                .filter(directory -> directory.getParentDirectoryId() == 0)
                .toList();
        User user = getAllUsers().get(0);

        List<Directory> actual = repository.getRootDirectories(user);

        assertEquals(expected, actual);
    }

    @Test
    void getDirectoriesInDirectoriesSucceeds(){
        List<Directory> expected = getDirectoriesForUser1()
                .stream()
                .filter(directory -> directory.getParentDirectoryId() == 1)
                .toList();

        List<Directory> actual = repository.getDirectoriesInDirectory(1);

        assertEquals(expected, actual);
    }

    @Test
    void getDirectoriesInDirectoriesReturnsEmptyListIfFails(){
        int idNotInDatabase = 100;
        List<Directory> actual = repository.getDirectoriesInDirectory(idNotInDatabase);

        assertEquals(0, actual.size());
    }

    @Test
    void getDirectoryByIdSucceeds() throws DataAccessException {
        Directory expectedDirectory = getDirectoriesForUser1().get(0);

        Directory actual = repository.getDirectoryById(1);

        assertEquals(expectedDirectory, actual);
    }

    @Test
    void getDirectoryByIdReturnsNullIfFails() throws DataAccessException {
        Directory actual = repository.getDirectoryById(100);

        assertNull(actual);
    }

    @Test
    void createDirectorySucceeds() throws DataAccessException {
        Directory directory = getDirectoryNotInDatabase();
        Directory expected = getDirectoryNotInDatabase();
        expected.setId(5);

        Directory actual = repository.createDirectory(directory);

        assertEquals(expected, actual);

    }
}