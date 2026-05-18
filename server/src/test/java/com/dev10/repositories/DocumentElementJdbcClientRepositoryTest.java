package com.dev10.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DocumentElementJdbcClientRepositoryTest {
    @Autowired
    DocumentElementJdbcClientRepository repository;

    @Autowired
    JdbcClient client;

    @BeforeEach
    void init(){
        client.sql("CALL set_known_good_state()");
    }

    @Test
    void createElementHappyPath(){

    }

    @Test
    void deleteElementHappyPath(){

    }

    @Test
    void deleteElementNotFoundReturnsFalse(){

    }

    @Test
    void createAttributeHappyPath(){

    }

    @Test
    void editElementAttributeHappyPath(){

    }

    @Test
    void editElementAttributeReturnsFalseIfAttributeNotFound(){

    }
}