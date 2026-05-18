package com.dev10.domain;

import com.dev10.TestDataHelper;
import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.Directory;
import com.dev10.models.Document;
import com.dev10.models.DocumentType;
import com.dev10.repositories.DirectoryRepository;
import com.dev10.repositories.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import javax.print.Doc;

import static com.dev10.TestDataHelper.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DocumentServiceTest {

    @MockitoBean
    DocumentRepository documentRepository;

    @MockitoBean
    DirectoryRepository directoryRepository;

    @Autowired
    DocumentService service;

    @Test
    void createDocumentSucceeds() throws DataAccessException {
        Document document = getDocumentNotInDatabase();
        Document expected = getDocumentNotInDatabase();
        expected.setId(7);
        when(documentRepository.createDocument(document)).thenReturn(expected);
        when(directoryRepository.getDirectoryById(document.getParentDirectoryId())).thenReturn(new Directory());

        Result<Document> result = service.createDocument(document);

        assertTrue(result.isSuccess());
        assertEquals(expected, result.getPayload());
        verify(documentRepository).createDocument(document);
    }

    @Test
    void createDocumentShouldFailIfModifiesId() throws DataAccessException {
        Document modifiedId = getDocumentNotInDatabase();
        modifiedId.setId(13);
        when(directoryRepository.getDirectoryById(modifiedId.getParentDirectoryId())).thenReturn(new Directory());

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

    @Test
    void updateDocumentHappyPath() throws DataAccessException{
        Document toUpdate = getDocumentsForUser1().get(0);
        toUpdate.setName("New name");
        toUpdate.setParentDirectoryId(3);
        when(directoryRepository.getDirectoryById(3)).thenReturn(new Directory());
        when(documentRepository.getDocumentById(toUpdate.getId())).thenReturn(toUpdate);
        when(documentRepository.updateDocument(toUpdate)).thenReturn(true);

        Result<Document> result = service.editDocument(toUpdate);

        assertTrue(result.isSuccess());
        assertEquals(toUpdate, result.getPayload());
        verify(documentRepository).updateDocument(toUpdate);
    }

    @Test
    void updateDocumentRejectsNullDocuments() throws DataAccessException{
        Document badDocument = null;

        Result<Document> result = service.editDocument(badDocument);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        verify(documentRepository, never()).updateDocument(any());

    }

    @Test
    void updateDocumentRequiresValidParentDirectoryId() throws DataAccessException{
        Document badDocument = getDocumentsForUser1().get(0);
        when(directoryRepository.getDirectoryById(badDocument.getParentDirectoryId())).thenReturn(null);
        when(documentRepository.getDocumentById(badDocument.getId())).thenReturn(badDocument);

        Result<Document> result = service.editDocument(badDocument);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        verify(documentRepository, never()).updateDocument(any());
    }

    @Test
    void updateDocumentDoesNotAllowTypeModification() throws DataAccessException{
        Document badDocument = getDocumentsForUser1().get(0);
        Document docWithTypeMismatch = getDocumentNotInDatabase();
        docWithTypeMismatch.setDocumentType(DocumentType.NOTE);
        when(directoryRepository.getDirectoryById(badDocument.getParentDirectoryId())).thenReturn(new Directory());
        when(documentRepository.getDocumentById(badDocument.getId())).thenReturn(docWithTypeMismatch);

        Result<Document> result = service.editDocument(badDocument);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        verify(documentRepository, never()).updateDocument(any());
    }

    @Test
    void updateDocumentCatchesConstraintValidations() throws DataAccessException{
        Document emptyName = getDocumentsForUser1().get(0);
        emptyName.setName("");
        when(directoryRepository.getDirectoryById(anyInt())).thenReturn(new Directory());
        when(documentRepository.getDocumentById(anyInt())).thenReturn(getDocumentsForUser1().get(0));

        Document nullName = getDocumentsForUser1().get(0);
        nullName.setName(null);

        Document nullDocumentType = getDocumentsForUser1().get(0);
        nullDocumentType.setDocumentType(null);

        Result<Document> emptyNameResult = service.editDocument(emptyName);
        Result<Document> nullNameResult = service.editDocument(nullName);
        Result<Document> nullDocumentTypeResult = service.editDocument(nullDocumentType);

        assertFalse(emptyNameResult.isSuccess());
        assertFalse(nullNameResult.isSuccess());
        assertFalse(nullDocumentTypeResult.isSuccess());

        verify(documentRepository, never()).createDocument(any());
    }
}