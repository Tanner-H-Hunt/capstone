package com.dev10.domain;

import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.Document;
import com.dev10.models.User;
import com.dev10.models.docelements.Element;
import com.dev10.repositories.DirectoryRepository;
import com.dev10.repositories.DocumentRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DirectoryRepository directoryRepository;
    private final ElementService elementService;
    private final Validator validator;

    public DocumentService(DocumentRepository documentRepository,
                           Validator validator,
                           DirectoryRepository directoryRepository,
                           ElementService elementService){
        this.documentRepository = documentRepository;
        this.validator = validator;
        this.directoryRepository = directoryRepository;
        this.elementService = elementService;
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

    public Result<Document> createDocument(Document document) throws DataAccessException {
        Result<Document> result = new Result<>();

        if(document == null){
            result.addErrorMessage("Cannot create a document with missing request parameters");
            return result;
        }

        if(document.getId() != 0){
            result.addErrorMessage("Cannot preemptively set a documents id");
        }

        if(directoryRepository.getDirectoryById(document.getParentDirectoryId()) == null){
            result.addErrorMessage("Document must have a valid, existing parent directory");
        }

        Set<ConstraintViolation<Document>> violations = validator.validate(document);
        for(ConstraintViolation<Document> violation : violations){
            result.addErrorMessage(violation.getMessage());
        }

        if(result.isSuccess()){
            Document updatedDocument = documentRepository.createDocument(document);
            result.setPayload(updatedDocument);
        }

        return result;
    }

    public Result<Document> editDocument(Document document) throws DataAccessException{
        Result<Document> result = new Result<>();
        if(document == null){
            result.addErrorMessage("Cannot create a document with missing request parameters");
            return result;
        }

        if(directoryRepository.getDirectoryById(document.getParentDirectoryId()) == null){
            result.addErrorMessage("Document must have a valid, existing parent directory");
        }

        if(document.getDocumentType() != documentRepository.getDocumentById(document.getId()).getDocumentType()){
            result.addErrorMessage("You may not change the document type");
        }

        Set<ConstraintViolation<Document>> violations = validator.validate(document);
        for(ConstraintViolation<Document> violation : violations){
            result.addErrorMessage(violation.getMessage());
        }

        if(result.isSuccess()){
            boolean updateSuccess = documentRepository.updateDocument(document);

            if(updateSuccess){
                result.setPayload(document);
            } else{
                result.addErrorMessage("Could not find the document to edit");
            }
        }

        return result;
    }

    public boolean delete(int id) throws DataAccessException {
        boolean result = true;

        List<Element> elements = elementService.getElementsForDocument(id);
        for(Element element : elements){
            result = result && (elementService.delete(element.getElementId()) > 0);
        }

        return documentRepository.deleteDocument(id) && result;
    }
}
