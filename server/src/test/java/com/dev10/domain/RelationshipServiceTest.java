package com.dev10.domain;

import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.Document;
import com.dev10.models.Relationship;
import com.dev10.models.docelements.DocumentElement;
import com.dev10.repositories.DocumentElementRepository;
import com.dev10.repositories.DocumentRepository;
import com.dev10.repositories.RelationshipRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RelationshipServiceTest {

    @MockitoBean
    RelationshipRepository relationshipRepository;

    @MockitoBean
    DocumentRepository documentRepository;

    @MockitoBean
    DocumentElementRepository elementRepository;

    @Autowired
    RelationshipService relationshipService;

    // delete and fetch relationships are pass through methods and won't be tested here

    @Test
    void createRelationshipHappyPath() throws DataAccessException {
        Relationship relationship = getRelationship();
        Relationship expected = getRelationship();
        expected.setId(1);
        when(elementRepository.getElementById(relationship.getElementId())).thenReturn(new DocumentElement());
        when(documentRepository.getDocumentById(relationship.getDocumentId())).thenReturn(new Document());
        when(relationshipRepository.create(relationship)).thenReturn(expected);

        Result<Relationship> result = relationshipService.create(relationship);

        assertTrue(result.isSuccess());
        assertEquals(expected, result.getPayload());
        verify(relationshipRepository).create(relationship);
    }

    @Test
    void createRelationshipAcceptsNullNames() throws DataAccessException {
        Relationship relationship = getRelationship();
        relationship.setName(null);
        Relationship expected = getRelationship();
        expected.setName(null);
        expected.setId(1);
        when(elementRepository.getElementById(relationship.getElementId())).thenReturn(new DocumentElement());
        when(documentRepository.getDocumentById(relationship.getDocumentId())).thenReturn(new Document());
        when(relationshipRepository.create(relationship)).thenReturn(expected);

        Result<Relationship> result = relationshipService.create(relationship);

        assertTrue(result.isSuccess());
        assertEquals(expected, result.getPayload());
        verify(relationshipRepository).create(relationship);
    }

    @Test
    void createRelationshipAcceptsBlankNames() throws DataAccessException {
        Relationship relationship = getRelationship();
        relationship.setName("");
        Relationship expected = getRelationship();
        expected.setName("");
        expected.setId(1);
        when(elementRepository.getElementById(relationship.getElementId())).thenReturn(new DocumentElement());
        when(documentRepository.getDocumentById(relationship.getDocumentId())).thenReturn(new Document());
        when(relationshipRepository.create(relationship)).thenReturn(expected);

        Result<Relationship> result = relationshipService.create(relationship);

        assertTrue(result.isSuccess());
        assertEquals(expected, result.getPayload());
        verify(relationshipRepository).create(relationship);
    }

    @Test
    void createRelationshipAcceptsNullDescriptions() throws DataAccessException {
        Relationship relationship = getRelationship();
        relationship.setDescription(null);
        Relationship expected = getRelationship();
        expected.setDescription(null);
        expected.setId(1);
        when(elementRepository.getElementById(relationship.getElementId())).thenReturn(new DocumentElement());
        when(documentRepository.getDocumentById(relationship.getDocumentId())).thenReturn(new Document());
        when(relationshipRepository.create(relationship)).thenReturn(expected);

        Result<Relationship> result = relationshipService.create(relationship);

        assertTrue(result.isSuccess());
        assertEquals(expected, result.getPayload());
        verify(relationshipRepository).create(relationship);
    }

    @Test
    void createRelationshipAcceptsEmptyDescriptions() throws DataAccessException {
        Relationship relationship = getRelationship();
        relationship.setDescription("");
        Relationship expected = getRelationship();
        expected.setDescription("");
        expected.setId(1);
        when(elementRepository.getElementById(relationship.getElementId())).thenReturn(new DocumentElement());
        when(documentRepository.getDocumentById(relationship.getDocumentId())).thenReturn(new Document());
        when(relationshipRepository.create(relationship)).thenReturn(expected);

        Result<Relationship> result = relationshipService.create(relationship);

        assertTrue(result.isSuccess());
        assertEquals(expected, result.getPayload());
        verify(relationshipRepository).create(relationship);
    }

    @Test
    void createRelationshipRejectsNullRelationships() throws DataAccessException {
        Result<Relationship> result = relationshipService.create(null);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        verify(relationshipRepository, never()).create(any());
    }

    @Test
    void createRelationshipFailsIfPresetId() throws DataAccessException {
        Relationship relationship = getRelationship();
        relationship.setId(5);
        when(elementRepository.getElementById(relationship.getElementId())).thenReturn(new DocumentElement());
        when(documentRepository.getDocumentById(relationship.getDocumentId())).thenReturn(new Document());

        Result<Relationship> result = relationshipService.create(relationship);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        verify(relationshipRepository, never()).create(any());

    }

    @Test
    void createRelationshipFailsIfElementDoesNotExist() throws DataAccessException {
        Relationship relationship = getRelationship();
        when(elementRepository.getElementById(relationship.getElementId())).thenReturn(null);
        when(documentRepository.getDocumentById(relationship.getDocumentId())).thenReturn(new Document());

        Result<Relationship> result = relationshipService.create(relationship);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        verify(relationshipRepository, never()).create(any());
    }

    @Test
    void createRelationshipFailsIfDocumentDoesNotExist() throws DataAccessException {
        Relationship relationship = getRelationship();
        relationship.setId(5);
        when(elementRepository.getElementById(relationship.getElementId())).thenReturn(new DocumentElement());
        when(documentRepository.getDocumentById(relationship.getDocumentId())).thenReturn(null);

        Result<Relationship> result = relationshipService.create(relationship);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        verify(relationshipRepository, never()).create(any());
    }

    @Test
    void createRelationshipFailsIfNameMoreThan50Characters() throws DataAccessException {
        Relationship relationship = getRelationship();
        relationship.setName(generateStringOfLength(51));
        when(elementRepository.getElementById(relationship.getElementId())).thenReturn(new DocumentElement());
        when(documentRepository.getDocumentById(relationship.getDocumentId())).thenReturn(new Document());

        Result<Relationship> result = relationshipService.create(relationship);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        verify(relationshipRepository, never()).create(any());
    }

    @Test
    void createRelationshipFailsIfDescriptionMoreThan250Characters() throws DataAccessException {
        Relationship relationship = getRelationship();
        relationship.setName(generateStringOfLength(251));
        when(elementRepository.getElementById(relationship.getElementId())).thenReturn(new DocumentElement());
        when(documentRepository.getDocumentById(relationship.getDocumentId())).thenReturn(new Document());

        Result<Relationship> result = relationshipService.create(relationship);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        verify(relationshipRepository, never()).create(any());
    }

    private Relationship getRelationship(){
        Relationship relationship = new Relationship();
        relationship.setId(0);
        relationship.setDocumentId(1);
        relationship.setElementId(1);
        relationship.setName("name");
        relationship.setDescription("description");
        return relationship;
    }

    private String generateStringOfLength(int length){
        return "a".repeat(length);
    }

    // TODO: update relationship will not be part of the MVP demo, so won't be tested yet
}