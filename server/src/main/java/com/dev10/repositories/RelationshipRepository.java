package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.Relationship;
import com.dev10.models.User;

import java.util.List;

public interface RelationshipRepository {
    Relationship create(Relationship relationship) throws DataAccessException;
    boolean delete(int relationshipId) throws DataAccessException;
    boolean edit(Relationship relationship) throws DataAccessException;
    List<Relationship> getRelationshipsForElement(int elementId) throws DataAccessException;
    List<Relationship> getRelationshipsForDocument(int documentId)  throws DataAccessException;
    Relationship getRelationshipById(int id) throws DataAccessException;
}
