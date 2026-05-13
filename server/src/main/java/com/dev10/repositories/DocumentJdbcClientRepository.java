package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.Document;
import com.dev10.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DocumentJdbcClientRepository implements DocumentRepository{
    @Override
    public List<Document> getDocumentsInDirectory(int directoryId) throws DataAccessException {
        return List.of();
    }

    @Override
    public List<Document> getDocumentsInRoot(User user) throws DataAccessException {
        return List.of();
    }

    @Override
    public List<Document> getAllDocuments(User user) throws DataAccessException {
        return List.of();
    }

    @Override
    public Document getDocumentById(int id) throws DataAccessException{
        return null;
    }

    @Override
    public Document createDocument(Document document) throws DataAccessException {
        return null;
    }

    @Override
    public boolean deleteDocument(Document document) throws DataAccessException {
        return false;
    }

    @Override
    public boolean updateDocument(Document document) throws DataAccessException {
        return false;
    }
}
