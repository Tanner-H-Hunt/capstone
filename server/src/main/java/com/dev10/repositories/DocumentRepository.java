package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.Document;
import com.dev10.models.User;

import javax.print.Doc;
import java.util.List;

public interface DocumentRepository {
    List<Document> getDocumentsInDirectory(int directoryId) throws DataAccessException;
    List<Document> getDocumentsInRoot(User user) throws DataAccessException;
    List<Document> getAllDocuments(User user) throws DataAccessException;
    Document getDocumentById(int id) throws DataAccessException;
    Document createDocument(Document document) throws DataAccessException;
    boolean deleteDocument(int id) throws DataAccessException;
    boolean updateDocument(Document document) throws DataAccessException;
}
