package com.dev10.repositories;

import com.dev10.models.Document;
import com.dev10.models.User;

import java.util.List;

public interface DocumentRepository {
    List<Document> getDocumentsInDirectory(User user, int directoryId);
    List<Document> getHomeDocuments(User user);
    List<Document> getAllDocuments(User user);
}
