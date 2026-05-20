package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.Relationship;
import com.dev10.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RelationshipJdbcClientRepository implements RelationshipRepository{

    private final JdbcClient client;

    public RelationshipJdbcClientRepository(JdbcClient client){
        this.client = client;
    }

    @Override
    public Relationship create(Relationship relationship) throws DataAccessException {
        return null;
    }

    @Override
    public Relationship delete(int relationshipId) throws DataAccessException {
        return null;
    }

    @Override
    public boolean edit(Relationship relationship) throws DataAccessException {
        return false;
    }

    @Override
    public List<Relationship> getRelationshipsForElement(int elementId)  throws DataAccessException {
        return null;
    }

    @Override
    public List<Relationship> getRelationshipsForDocument(int documentId) throws DataAccessException{
        return null;
    }

    @Override
    public Relationship getRelationshipById(int id)  throws DataAccessException{
        return null;
    }
}
