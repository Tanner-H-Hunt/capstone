package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static com.dev10.TestDataHelper.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ElementJdbcClientRepositoryTest {
    @Autowired
    ElementJdbcClientRepository repository;

    @Autowired
    JdbcClient client;

    @BeforeEach
    void init(){
        client.sql("CALL set_known_good_state()").update();
    }

    @Test
    void createElementHappyPath() throws DataAccessException {
        Element element = getBoxNotInDatabase();

        Element created = repository.createElement(element);

        assertEquals(element, created);
        assertEquals(
                getElementsForUser1Uml().size() + 1,
                repository.getElementsForDocument(2).size()
        );
    }

    @Test
    void getElementsForDocumentHappyPath() throws DataAccessException {
        List<Element> expected = getElementsForUser1Uml();

        List<Element> actual = repository.getElementsForDocument(2);

        assertEquals(expected, actual);
    }

    @Test
    void getElementsForDocumentReturnsEmptyListIfNoElementsFound() throws DataAccessException {
        List<Element> actual = repository.getElementsForDocument(1);

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    void getElementByIdHappyPath() throws DataAccessException {
        Element expected = getElementsForUser1Uml().get(0);

        Element actual = repository.getElementById(1);

        assertEquals(expected, actual);
    }

    @Test
    void getElementByIdReturnsNullIfNoIdFound() throws DataAccessException {
        Element actual = repository.getElementById(999);

        assertNull(actual);
    }

    @Test
    void getAttributesForElementHappyPath() throws DataAccessException {
        List<Attribute> expected = getAttributesForElement1();

        List<Attribute> actual = repository.getAttributesForElement(1);

        assertEquals(expected, actual);
    }

    @Test
    void getAttributesForElementReturnsEmptyListIfNoAttributesFound() throws DataAccessException {
        List<Attribute> actual = repository.getAttributesForElement(999);

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    void getAttributeByIdHappyPath() throws DataAccessException {
        Attribute expected = getAttribute1();

        Attribute actual = repository.getAttributeById(1);

        assertEquals(expected, actual);
    }

    @Test
    void getAttributeByIdReturnsNullIfNotFound() throws DataAccessException {
        Attribute actual = repository.getAttributeById(999);

        assertNull(actual);
    }

    @Test
    void getAttributeByJsonKeyHappyPath() throws DataAccessException {
        Attribute expected = getAttribute1();

        Attribute actual = repository.getAttributeByJsonKey(1, "startXPos");

        assertEquals(expected, actual);
    }

    @Test
    void getAttributeByJsonKeyReturnsNullIfNotFound() throws DataAccessException {
        Attribute actual = repository.getAttributeByJsonKey(1, "doesNotExist");

        assertNull(actual);
    }

    @Test
    void deleteElementReturnsSumOfAttributesAndElementDeleted() throws DataAccessException {

        int result = repository.deleteElement(1);

        // element + 4 attributes
        assertEquals(5, result);

        assertNull(repository.getElementById(1));
        assertTrue(repository.getAttributesForElement(1).isEmpty());
    }

    @Test
    void deleteElementNotFoundReturnsZero() throws DataAccessException {

        int result = repository.deleteElement(999);

        assertEquals(0, result);
    }

    @Test
    void createAttributeHappyPath() throws DataAccessException {

        Attribute attribute = getAttributeNotInDatabase();

        Attribute created = repository.createAttribute(attribute);

        assertNotNull(created);
        assertTrue(created.getAttributeId() > 0);

        List<Attribute> attributes = repository.getAttributesForElement(1);

        assertEquals(5, attributes.size());
    }

    @Test
    void editAttributeHappyPath() throws DataAccessException {

        Attribute updated = getUpdatedAttribute1();

        boolean result = repository.editAttribute(updated);

        Attribute actual = repository.getAttributeById(1);

        assertTrue(result);
        assertEquals(updated, actual);
    }

    @Test
    void editAttributeReturnsFalseIfAttributeNotFound() throws DataAccessException {

        Attribute attribute = getAttributeNotInDatabase();
        attribute.setAttributeId(999);

        boolean result = repository.editAttribute(attribute);

        assertFalse(result);
    }

    @Test
    void getUserForElementByElementIdHappyPath() throws DataAccessException {

        var expected = getAllUsers().get(0); // user1 (a@a.com)

        var actual = repository.getUserForElementByElementId(1);

        assertEquals(expected, actual);
    }

    @Test
    void getUserForElementByElementIdReturnsNullIfNotFound() throws DataAccessException {

        var actual = repository.getUserForElementByElementId(999);

        assertNull(actual);
    }

    @Test
    void getUserForAttributeByAttributeIdHappyPath() throws DataAccessException {

        var expected = getAllUsers().get(0); // user1 (a@a.com)

        var actual = repository.getUserForAttributeByAttributeId(1);

        assertEquals(expected, actual);
    }

    @Test
    void getUserForAttributeByAttributeIdReturnsNullIfNotFound() throws DataAccessException {

        var actual = repository.getUserForAttributeByAttributeId(999);

        assertNull(actual);
    }
}