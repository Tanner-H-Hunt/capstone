package com.dev10.repositories;

import com.dev10.models.Document;
import com.dev10.models.User;

import java.util.List;

public class DocumentJdbcClientRepository implements DocumentRepository{
    @Override
    public List<Document> getDocumentsInDirectory(User user, int directoryId) {
        return List.of();
    }

    @Override
    public List<Document> getHomeDocuments(User user) {
        return List.of();
    }

    @Override
    public List<Document> getAllDocuments(User user) {
        return List.of();
    }
}
