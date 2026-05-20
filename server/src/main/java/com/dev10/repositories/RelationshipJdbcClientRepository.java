package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.Relationship;
import com.dev10.models.User;
import com.dev10.models.mappers.RelationshipRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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
        final String sql = """
                INSERT INTO document_element_link (element_id, document_id, name, description) values
                (:element_id, :document_id, :name, :description);
                """;

        try{
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            client.sql(sql)
                    .param("element_id", relationship.getElementId())
                    .param("document_id", relationship.getDocumentId())
                    .param("name", relationship.getName())
                    .param("description", relationship.getDescription())
                    .update(keyHolder);

            relationship.setId(keyHolder.getKey().intValue());
            return relationship;
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to ", e);
        }
    }

    @Override
    public boolean delete(int relationshipId) throws DataAccessException {
        final String sql = """
                DELETE FROM document_element_link
                WHERE document_element_link_id = :id;
                """;

        try{
            return client.sql(sql)
                    .param("id", relationshipId)
                    .update() > 0;
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to ", e);
        }
    }

    @Override
    public boolean edit(Relationship relationship) throws DataAccessException {
        final String sql = """
                UPDATE document_element_link
                SET name = :name, description = :description
                WHERE document_element_link_id = :id;
                """;

        try{
            return client.sql(sql)
                    .param("name", relationship.getName())
                    .param("description", relationship.getDescription())
                    .param("id", relationship.getId())
                    .update() > 0;
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to ", e);
        }
    }

    @Override
    public List<Relationship> getRelationshipsForElement(int elementId)  throws DataAccessException {
        final String sql = """
                SELECT
                    document_element_link_id as relation_id,
                    element_id,
                    document_id,
                    name,
                    description
                FROM document_element_link
                WHERE element_id = :id;
                """;

        try{
            return client.sql(sql)
                    .param("id", elementId)
                    .query(new RelationshipRowMapper())
                    .list();
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to fetch relationships for an element", e);
        }
    }

    @Override
    public List<Relationship> getRelationshipsForDocument(int documentId) throws DataAccessException{
        final String sql = """
                SELECT
                    document_element_link_id as relation_id,
                    element_id,
                    document_id,
                    name,
                    description
                FROM document_element_link
                WHERE document_id = :id;
                """;

        try{
            return client.sql(sql)
                    .param("id", documentId)
                    .query(new RelationshipRowMapper())
                    .list();
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to fetch relationships for a document", e);
        }
    }

    @Override
    public Relationship getRelationshipById(int id)  throws DataAccessException{
        final String sql = """
                SELECT
                    document_element_link_id as relation_id,
                    element_id,
                    document_id,
                    name,
                    description
                FROM document_element_link
                WHERE document_element_link_id = :id;
                """;

        try{
            return client.sql(sql)
                    .param("id", id)
                    .query(new RelationshipRowMapper())
                    .optional().orElse(null);
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to fetch relationships by ID", e);
        }
    }
}
