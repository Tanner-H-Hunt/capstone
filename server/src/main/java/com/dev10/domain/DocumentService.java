package com.dev10.domain;

import com.dev10.models.DataAccessException;
import com.dev10.models.Document;
import com.dev10.models.User;
import com.dev10.repositories.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository){
        this.documentRepository = documentRepository;
    }

    public List<Document> getDocumentsInDirectory(int directoryId) throws DataAccessException {
        return documentRepository.getDocumentsInDirectory(directoryId);
    }

    public List<Document> getDocumentsInRoot(User user) throws DataAccessException {
        return documentRepository.getDocumentsInRoot(user);
    }

    public List<Document> getAllDocuments(User user) throws DataAccessException{
        return documentRepository.getAllDocuments(user);
    }

    public Document getDocumentById(int id) throws DataAccessException{
        return documentRepository.getDocumentById(id);
    }
}
