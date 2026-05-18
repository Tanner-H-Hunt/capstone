package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.Document;
import com.dev10.models.DocumentType;
import com.dev10.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import static com.dev10.TestDataHelper.*;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DocumentJdbcClientRepositoryTest {

    @Autowired
    DocumentJdbcClientRepository repository;

    @Autowired
    JdbcClient client;

    @BeforeEach
    void init(){
        client.sql("CALL set_known_good_state();").update();
    }

    @Test
    void getsCorrectRootDocuments() throws DataAccessException {
        // result set for first user
        User user = getAllUsers().get(0);
        List<Document> expected = getDocumentsForUser1()
                .stream()
                .filter(document -> document.getParentDirectoryId() == 1)
                .toList();

        List<Document> results = repository.getDocumentsInRoot(user);

        assertEquals(2, results.size());
        assertEquals(expected, results);

        // result set for second user
        user = getAllUsers().get(1);
        expected = getDocumentsForUser2()
                .stream()
                .filter(document -> document.getParentDirectoryId() == 2)
                .toList();

        results = repository.getDocumentsInRoot(user);

        assertEquals(3, results.size());
        assertEquals(expected, results);
    }

    @Test
    void getDocumentsInSubdirectorySucceeds() throws DataAccessException {
        int subdirectoryWithDocuments = 3;
        List<Document> expected = getDocumentsForUser1()
                .stream()
                .filter(document -> document.getParentDirectoryId() == 3)
                .toList();

        List<Document> actual = repository.getDocumentsInDirectory(subdirectoryWithDocuments);

        assertEquals(1, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void getDocumentsInSubdirectoryReturnsEmptyListIfSubdirectoryNotFound() throws DataAccessException {
        int idOfSubdirectoryThatDoesntExist = 100;

        List<Document> actual = repository.getDocumentsInDirectory(idOfSubdirectoryThatDoesntExist);

        assertEquals(0, actual.size());
    }

    @Test
    void getDocumentsInSubdirectoryReturnsEmptyListIfSubdirectoryEmpty() throws DataAccessException {
        int idOfSubdirectory = 4;

        List<Document> actual = repository.getDocumentsInDirectory(idOfSubdirectory);

        assertEquals(0, actual.size());
    }

    @Test
    void getDocumentByIdSucceeds() throws DataAccessException {
        int id = 1;
        Document expected = getDocumentsForUser1().get(0);

        Document actual = repository.getDocumentById(id);

        assertEquals(expected, actual);
    }

    @Test
    void getDocumentByIdReturnsNullIfNotFound() throws DataAccessException {
        int idNotInDatabase = 100;

        Document actual = repository.getDocumentById(idNotInDatabase);

        assertNull(actual);
    }

    @Test
    void getAllDocumentsSucceeds() throws DataAccessException{
        List<Document> expected = getDocumentsForUser1();
        User user1 = getAllUsers().get(0);

        List<Document> actual = repository.getAllDocuments(user1);
        // sorting the return value fixes bug where sometimes this test fails because the list is out of order
        actual = actual.stream().sorted(Comparator.comparing(Document::getId)).toList();

        assertEquals(expected, actual);
    }

    @Test
    void getAllDocumentsReturnsEmptyListIfUserNotFound() throws DataAccessException {
        User user = getUserNotInDatabase();

        List<Document> actual = repository.getAllDocuments(user);

        assertEquals(0, actual.size());
    }

    @Test
    void createDocumentHappyPath() throws DataAccessException {
        Document document = new Document();
        document.setName("TEST DOCUMENT");
        document.setDocumentType(DocumentType.NOTE);
        document.setParentDirectoryId(1);

        Document actual = repository.createDocument(document);

        assertEquals(document, actual);
    }

    @Test
    void updateDocumentHappyPath() throws DataAccessException {
        Document document = getDocumentsForUser1().get(0);
        document.setName("updated doc");
        document.setParentDirectoryId(3);

        boolean result = repository.updateDocument(document);

        assertTrue(result);
        assertEquals(document, repository.getDocumentById(document.getId()));
    }

    @Test
    void updateDocumentNoDocumentFound() throws DataAccessException {
        Document document = getDocumentNotInDatabase();

        boolean result = repository.updateDocument(document);

        assertFalse(result);
    }

    @Test
    void deleteDocumentHappyPath() throws DataAccessException {
        int validId = 1;
        List<Document> expected = getDocumentsForUser1()
                .stream()
                .filter(document -> document.getId() != validId)
                .toList();

        boolean result = repository.deleteDocument(validId);

        assertTrue(result);
        assertEquals(expected,
                repository.getAllDocuments(getAllUsers().get(0))
                        .stream()
                        .sorted(Comparator.comparing(Document::getId))
                        .toList());
    }

    @Test
    void deleteDocumentNotFound() throws DataAccessException {
        int invalidId = 100;
        List<Document> expectedUser1 = getDocumentsForUser1();

        List<Document> expectedUser2 = getDocumentsForUser2();

        boolean result = repository.deleteDocument(invalidId);

        assertFalse(result);
        assertEquals(expectedUser1, repository.getAllDocuments(getAllUsers().get(0)).stream()
                .sorted(Comparator.comparing(Document::getId))
                .toList());
        assertEquals(expectedUser2, repository.getAllDocuments(getAllUsers().get(1)).stream()
                .sorted(Comparator.comparing(Document::getId))
                .toList());
    }
}