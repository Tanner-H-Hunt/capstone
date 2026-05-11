package com.dev10.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.dev10.models.DataAccessException;
import com.dev10.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import static com.dev10.TestDataHelper.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcClientRepositoryTest {

    @Autowired
    UserJdbcClientRepository repository;

    @Autowired
    JdbcClient client;

    @BeforeEach
    void setup(){
        client.sql("CALL set_known_good_state()").update();
    }

    @Test
    void successfullyFindsUserByEmail() throws DataAccessException {
        User inDatabase = getAllUsers().get(0);

        User result = repository.getUserWithEmail(inDatabase.getEmail());

        assertEquals(inDatabase, result);
    }

    @Test
    void findingNoUsersWithEmailReturnsNull() throws DataAccessException {
        User notInDatabase = getUserNotInDatabase();

        User result = repository.getUserWithEmail(notInDatabase.getEmail());

        assertNull(result);
    }

    @Test
    void createUserHappyPath() throws DataAccessException {
        User userNotInDatabase = getUserNotInDatabase();

        User result = repository.createUser(userNotInDatabase);

        assertEquals(3, result.getId());
        assertEquals(userNotInDatabase, result);
    }
}