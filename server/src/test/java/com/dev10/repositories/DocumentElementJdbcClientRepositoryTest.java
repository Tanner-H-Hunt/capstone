package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.docelements.DocumentElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static com.dev10.TestDataHelper.*;

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
    void createElementHappyPath() throws DataAccessException {
        DocumentElement element = getBoxNotInDatabase();

        DocumentElement created = repository.create(element);

        assertEquals(element, created);
        assertEquals(getElementsForUser1Uml().size() + 1, repository.getElementsForDocument(2).size());
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

    @Test
    void getElementsForDocumentHappyPath() throws DataAccessException {
        List<DocumentElement> expected = getElementsForUser1Uml();
        List<DocumentElement> actual = repository.getElementsForDocument(2);

        assertEquals(expected, actual);
    }
}