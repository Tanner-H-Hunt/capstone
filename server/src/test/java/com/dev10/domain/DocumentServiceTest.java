package com.dev10.domain;

import com.dev10.TestDataHelper;
import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.Document;
import com.dev10.repositories.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static com.dev10.TestDataHelper.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DocumentServiceTest {

    @MockitoBean
    DocumentRepository documentRepository;

    @Autowired
    DocumentService service;

    @Test
    void createDocumentSucceeds() throws DataAccessException {
        Document document = getDocumentNotInDatabase();
        Document expected = getDocumentNotInDatabase();
        expected.setId(7);
        when(documentRepository.createDocument(document)).thenReturn(expected);

        Result<Document> result = service.createDocument(document);

        assertTrue(result.isSuccess());
        assertEquals(expected, result.getPayload());
        verify(documentRepository).createDocument(document);
    }

    @Test
    void createDocumentShouldFailIfModifiesId() throws DataAccessException {
        Document modifiedId = getDocumentNotInDatabase();
        modifiedId.setId(13);

        Result<Document> result = service.createDocument(modifiedId);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getErrorMessages().size());
        verify(documentRepository, never()).createDocument(any());
    }

    @Test
    void createDocumentShouldFailIfDocumentIsNull() throws DataAccessException {
        Document nullDocument = null;

        Result<Document> result = service.createDocument(nullDocument);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getErrorMessages().size());
        verify(documentRepository, never()).createDocument(any());
    }

    @Test
    void createDocumentShouldFailIfInvalidParentDirectory() throws DataAccessException {
        int parentDirectoryIdNotInDatabase = 50;
        Document invalidParentDirectory = getDocumentNotInDatabase();
        invalidParentDirectory.setParentDirectoryId(parentDirectoryIdNotInDatabase);

        Result<Document> result = service.createDocument(invalidParentDirectory);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getErrorMessages().size());
        verify(documentRepository, never()).createDocument(any());
    }

    @Test
    void createDocumentShouldFailIfAnyFieldIsNullOrBlank() throws DataAccessException {
        Document emptyName = getDocumentNotInDatabase();
        emptyName.setName("");

        Document nullName = getDocumentNotInDatabase();
        nullName.setName(null);

        Document nullDocumentType = getDocumentNotInDatabase();
        nullDocumentType.setDocumentType(null);

        Result<Document> emptyNameResult = service.createDocument(emptyName);
        Result<Document> nullNameResult = service.createDocument(nullName);
        Result<Document> nullDocumentTypeResult = service.createDocument(nullDocumentType);

        assertFalse(emptyNameResult.isSuccess());
        assertFalse(nullNameResult.isSuccess());
        assertFalse(nullDocumentTypeResult.isSuccess());

        verify(documentRepository, never()).createDocument(any());

    }
}