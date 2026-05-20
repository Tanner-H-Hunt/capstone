package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.User;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import com.dev10.models.mappers.AttributeRowMapper;
import com.dev10.models.mappers.DocumentElementRowMapper;
import com.dev10.models.mappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DocumentElementJdbcClientRepository implements DocumentElementRepository{
    @Autowired
    JdbcClient client;

    private final String baseSelect = """
                SELECT
                    de.document_element_id as documentElementId,
                    det.`type` as documentElementType,
                    de.document_id as documentId
                FROM document_element de
                INNER JOIN element_type det ON de.element_type_id = det.element_type_id
            """;

    @Override
    public List<DocumentElement> getElementsForDocument(int id) throws DataAccessException {
        final String sql = baseSelect + " WHERE de.document_id = :id;";

        try{
            return client.sql(sql)
                    .param("id", id)
                    .query(new DocumentElementRowMapper())
                    .list();
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to fetch data for this document", e);
        }
    }

    @Override
    public DocumentElement getElementById(int id) throws DataAccessException {
        final String sql = baseSelect + " Where de.document_element_id = :id";

        try{
            return client.sql(sql)
                    .param("id", id)
                    .query(new DocumentElementRowMapper())
                    .optional().orElse(null);

        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to fetch data for this document", e);
        }
    }

    @Override
    public DocumentElement createElement(DocumentElement documentElement) throws DataAccessException {
        final String sql = """
                INSERT INTO document_element (element_type_id, document_id) values
                ((SELECT element_type_id FROM element_type WHERE `type` = :type), :document_id)
                """;

        try{
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            client.sql(sql)
                    .param("type", documentElement.getDocumentElementType().toString())
                    .param("document_id", documentElement.getDocumentId())
                    .update(keyHolder);

            documentElement.setDocumentElementId(keyHolder.getKey().intValue());
            return documentElement;

        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to create a new element", e);
        }
    }

    @Override
    public int deleteElement(int id) throws DataAccessException {
        final String attributeSql = "DELETE FROM `attribute` where document_element_id = :id;";

        final String elementSql = "DELETE FROM document_element where document_element_id = :id;";

        try {
            int deletedAttributes =  client.sql(attributeSql)
                    .param("id", id)
                    .update();

            int deletedElements = client.sql(elementSql)
                    .param("id", id)
                    .update();

            return deletedElements + deletedAttributes;

        } catch (Exception e){
            throw new DataAccessException("something went wrong while trying to delete an element", e);
        }
    }

    @Override
    public List<Attribute> getAttributesForElement(int documentElementId) throws DataAccessException {
        final String sql = """
                SELECT * FROM `attribute` WHERE document_element_id = :id;
                """;
        try{
            return client.sql(sql)
                    .param("id", documentElementId)
                    .query(new AttributeRowMapper())
                    .list();

        } catch (Exception e){
            throw new DataAccessException("Something went wrong while fetching the attributes for an element", e);
        }
    }

    @Override
    public Attribute getAttributeById(int id) throws DataAccessException {
        final String sql = """
                SELECT * FROM `attribute` WHERE attribute_id = :id;
                """;
        try{
            return client.sql(sql)
                    .param("id", id)
                    .query(new AttributeRowMapper())
                    .optional().orElse(null);

        } catch (Exception e){
            throw new DataAccessException("Something went wrong while fetching the attributes for an element", e);
        }
    }

    @Override
    public Attribute getAttributeByJsonKey(int elementId, String key) throws DataAccessException {
        final String sql = """
                SELECT * FROM attribute
                WHERE document_element_id = :element_id
                AND value LIKE :pattern;
                """;

        try{
            return client.sql(sql)
                    .param("element_id", elementId)
                    .param("pattern", "_" +  key + "_%")
                    .query(new AttributeRowMapper())
                    .optional().orElse(null);

        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to fetch an attribute by key", e);
        }
    }

    @Override
    public Attribute createAttribute(Attribute attribute) throws DataAccessException {
        final String sql = """
                insert into attribute (document_element_id, value) values
                (:document_element_id, :value);
                """;

        try{
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            client.sql(sql)
                    .param("document_element_id", attribute.getDocumentElementId())
                    .param("value", attribute.getValue())
                    .update(keyHolder);

            attribute.setAttributeId(keyHolder.getKey().intValue());
            return attribute;

        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to create an attribute", e);
        }

    }

    @Override
    public boolean editAttribute(Attribute attribute) throws DataAccessException {
        final String sql = """
                UPDATE attribute
                SET value = :value
                WHERE attribute_id = :id;
                """;

        try{
            return client.sql(sql)
                    .param("value", attribute.getValue())
                    .param("id", attribute.getAttributeId())
                    .update() > 0;
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to edit an attribute", e);
        }

    }

    @Override
    public User getUserForElementByElementId(int id) throws DataAccessException {
        final String sql = """
                select distinct
                    acc.account_id,
                    acc.email,
                    acc.password,
                    acc.password_salt
                from document_element e
                INNER JOIN document d on d.document_id = e.document_id
                INNER JOIN directory dir on dir.directory_id = d.directory_id
                INNER JOIN account acc on acc.account_id = dir.account_id
                WHERE e.document_element_id = :id;
                """;

        try{
            return client.sql(sql)
                    .param("id", id)
                    .query(new UserRowMapper())
                    .optional().orElse(null);
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to fetch the user associated with this element", e);
        }
    }

    @Override
    public User getUserForAttributeByAttributeId(int id) throws DataAccessException {
        final String sql = """
                SELECT DISTINCT
                    acc.account_id,
                    acc.email,
                    acc.password,
                    acc.password_salt
                FROM attribute a
                INNER JOIN document_element e on a.document_element_id = e.document_element_id
                INNER JOIN document d on d.document_id = e.document_id
                INNER JOIN directory dir on dir.directory_id = d.directory_id
                INNER JOIN account acc on acc.account_id = dir.account_id
                WHERE a.attribute_id = :id;
                """;

        try{
            return client.sql(sql)
                    .param("id", id)
                    .query(new UserRowMapper())
                    .optional().orElse(null);
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to fetch the user associated with this element", e);
        }
    }
}
