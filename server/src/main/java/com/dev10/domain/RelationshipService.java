package com.dev10.domain;

import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.Document;
import com.dev10.models.Relationship;
import com.dev10.repositories.DocumentElementRepository;
import com.dev10.repositories.DocumentRepository;
import com.dev10.repositories.RelationshipRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RelationshipService {

    private final RelationshipRepository relationshipRepository;
    private final DocumentElementRepository elementRepository;
    private final DocumentRepository documentRepository;
    private final Validator validator;

    public RelationshipService(RelationshipRepository relationshipRepository,
                               Validator validator,
                               DocumentElementRepository elementRepository,
                               DocumentRepository documentRepository){
        this.relationshipRepository = relationshipRepository;
        this.validator = validator;
        this.elementRepository = elementRepository;
        this.documentRepository = documentRepository;
    }

    public Result<Relationship> create(Relationship relationship) throws DataAccessException {
        Result<Relationship> result = new Result<>();

        if(relationship == null){
            result.addErrorMessage("relationship cannot be null");
            return result;
        }

        if(relationship.getId() != 0){
            result.addErrorMessage("You may not preset the id of a relationship");
        }

        Set<ConstraintViolation<Relationship>> violations = validator.validate(relationship);
        for(ConstraintViolation<Relationship> violation : violations){
            result.addErrorMessage(violation.getMessage());
        }

        if(documentRepository.getDocumentById(relationship.getDocumentId()) == null){
            result.addErrorMessage("Relationship must reference a valid document");
        }

        if(elementRepository.getElementById(relationship.getElementId()) == null){
            result.addErrorMessage("Relationship must reference a valid element");
        }

        if(result.isSuccess()){
            Relationship createResult = relationshipRepository.create(relationship);
            result.setPayload(createResult);
        }

        return result;
    }

    public List<Relationship> getRelationshipsForElement(int id) throws DataAccessException {
        return relationshipRepository.getRelationshipsForElement(id);
    }

    public List<Relationship> getRelationshipsForDocument(int id) throws DataAccessException {
        return relationshipRepository.getRelationshipsForDocument(id);
    }

    public Relationship getRelationshipById(int id) throws DataAccessException {
        return relationshipRepository.getRelationshipById(id);
    }

    public boolean delete(int id) throws DataAccessException {
        return relationshipRepository.delete(id);
    }

    public Document getOriginatingDocumentForRelationshipElement(int relationshipId) throws DataAccessException {
        return relationshipRepository.getOriginatingDocumentForRelationshipElement(relationshipId);
    }

    public boolean update(Relationship relationship){
        return false;
    }
}
