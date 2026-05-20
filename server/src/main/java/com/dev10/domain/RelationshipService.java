package com.dev10.domain;

import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.Relationship;
import com.dev10.repositories.RelationshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationshipService {

    private final RelationshipRepository relationshipRepository;

    public RelationshipService(RelationshipRepository relationshipRepository){
        this.relationshipRepository = relationshipRepository;
    }

    public Result<Relationship> create(Relationship relationship){
        return null;
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

    public boolean update(Relationship relationship){
        return false;
    }
}
