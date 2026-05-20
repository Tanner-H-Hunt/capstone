package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.Relationship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static com.dev10.TestDataHelper.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RelationshipJdbcClientRepositoryTest {

    @Autowired
    RelationshipJdbcClientRepository repository;

    @Autowired
    JdbcClient client;

    @BeforeEach
    void init(){
        client.sql("CALL set_known_good_state();").update();
    }

    @Test
    void createRelationshipHappyPath() throws DataAccessException {

        Relationship relationship = new Relationship();
        relationship.setElementId(2);
        relationship.setDocumentId(1);

        Relationship created = repository.create(relationship);

        assertNotNull(created);
        assertTrue(created.getId() > 0);
        assertEquals(2, created.getElementId());
        assertEquals(1, created.getDocumentId());

        Relationship found = repository.getRelationshipById(created.getId());

        assertNotNull(found);
        assertEquals(created.getId(), found.getId());
    }

    @Test
    void deleteRelationshipHappyPath() throws DataAccessException {

        Relationship deleted = repository.delete(1);

        assertNotNull(deleted);
        assertEquals(1, deleted.getId());

        Relationship found =
                repository.getRelationshipById(1);

        assertNull(found);
    }

    @Test
    void deleteRelationshipRelationshipNotFoundReturnsFalse() throws DataAccessException {

        Relationship deleted = repository.delete(9999);

        assertNull(deleted);
    }

    @Test
    void getRelationshipsForElementHappyPath() throws DataAccessException {

        List<Relationship> relationships = repository.getRelationshipsForElement(2);

        assertNotNull(relationships);
        assertFalse(relationships.isEmpty());

        assertTrue( relationships.stream()
                        .allMatch(r -> r.getElementId() == 2)
        );
    }

    @Test
    void getRelationshipsForElementReturnsEmptyListIfNotFound() throws DataAccessException {

        List<Relationship> relationships = repository.getRelationshipsForElement(9999);

        assertNotNull(relationships);
        assertTrue(relationships.isEmpty());
    }

    @Test
    void getRelationshipsForDocumentHappyPath() throws DataAccessException {

        List<Relationship> relationships = repository.getRelationshipsForDocument(1);

        assertNotNull(relationships);
        assertFalse(relationships.isEmpty());

        assertTrue(
                relationships.stream()
                        .allMatch(r -> r.getDocumentId() == 1)
        );
    }

    @Test
    void getRelationshipsForDocumentReturnsEmptyListIfNotFound() throws DataAccessException {

        List<Relationship> relationships = repository.getRelationshipsForDocument(9999);

        assertNotNull(relationships);
        assertTrue(relationships.isEmpty());
    }

    @Test
    void editHappyPath() throws DataAccessException {

        Relationship relationship =
                repository.getRelationshipById(1);

        assertNotNull(relationship);

        relationship.setName("Updated Name");
        relationship.setDescription("Updated Description");

        boolean result = repository.edit(relationship);

        assertTrue(result);

        Relationship updated = repository.getRelationshipById(1);

        assertEquals("Updated Name", updated.getName());
        assertEquals("Updated Description", updated.getDescription());
    }

    @Test
    void editReturnsFalseIfNotFound() throws DataAccessException {

        Relationship relationship = new Relationship();
        relationship.setId(9999);
        relationship.setElementId(2);
        relationship.setDocumentId(1);

        boolean result = repository.edit(relationship);

        assertFalse(result);
    }

    @Test
    void editDoesNotModifyDocumentId() throws DataAccessException {

        Relationship relationship =
                repository.getRelationshipById(1);

        assertNotNull(relationship);

        int originalDocumentId = relationship.getDocumentId();

        relationship.setDocumentId(999);

        repository.edit(relationship);

        Relationship updated = repository.getRelationshipById(1);

        assertEquals(
                originalDocumentId,
                updated.getDocumentId()
        );
    }

    @Test
    void editDoesNotModifyElementId() throws DataAccessException {

        Relationship relationship = repository.getRelationshipById(1);

        assertNotNull(relationship);

        int originalElementId = relationship.getElementId();

        relationship.setElementId(999);

        repository.edit(relationship);

        Relationship updated = repository.getRelationshipById(1);

        assertEquals(
                originalElementId,
                updated.getElementId()
        );
    }

    @Test
    void getRelationshipByIdHappyPath() throws DataAccessException {

        Relationship relationship = repository.getRelationshipById(1);

        assertNotNull(relationship);
        assertEquals(1, relationship.getId());
    }

    @Test
    void getRelationshipByIdReturnsNullIfNotFound() throws DataAccessException {

        Relationship relationship = repository.getRelationshipById(9999);

        assertNull(relationship);
    }
}