package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.Relationship;

public interface RelationshipRepository {
    Relationship create(int elementId, int documentId) throws DataAccessException;
    Relationship delete(int relationshipId) throws DataAccessException;
}
