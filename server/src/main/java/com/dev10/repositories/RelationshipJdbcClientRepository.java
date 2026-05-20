package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.Relationship;
import com.dev10.models.User;

public class RelationshipJdbcClientRepository implements RelationshipRepository{
    @Override
    public Relationship create(int elementId, int documentId) throws DataAccessException {
        return null;
    }

    @Override
    public Relationship delete(int relationshipId) throws DataAccessException {
        return null;
    }

    @Override
    public User getUserForRelationshipByRelationshipId(int id) {
        return null;
    }
}
