package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.User;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import com.dev10.models.mappers.AttributeRowMapper;
import com.dev10.models.mappers.ElementRowMapper;
import com.dev10.models.mappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ElementJdbcClientRepository implements ElementRepository {
    @Autowired
    JdbcClient client;

    private final String baseSelect = """
                SELECT
                    e.element_id as elementId,
                    et.`type` as elementType,
                    e.document_id as documentId
                FROM element e
                INNER JOIN element_type et ON e.element_type_id = et.element_type_id
            """;

    @Override
    public List<Element> getElementsForDocument(int id) throws DataAccessException {
        final String sql = baseSelect + " WHERE e.document_id = :id;";

        try{
            return client.sql(sql)
                    .param("id", id)
                    .query(new ElementRowMapper())
                    .list();
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to fetch data for this document", e);
        }
    }

    @Override
    public Element getElementById(int id) throws DataAccessException {
        final String sql = baseSelect + " Where e.element_id = :id";

        try{
            return client.sql(sql)
                    .param("id", id)
                    .query(new ElementRowMapper())
                    .optional().orElse(null);

        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to fetch data for this document", e);
        }
    }

    @Override
    public Element createElement(Element element) throws DataAccessException {
        final String sql = """
                INSERT INTO element (element_type_id, document_id) values
                ((SELECT element_type_id FROM element_type WHERE `type` = :type), :document_id)
                """;

        try{
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            client.sql(sql)
                    .param("type", element.getElementType().toString())
                    .param("document_id", element.getDocumentId())
                    .update(keyHolder);

            element.setElementId(keyHolder.getKey().intValue());
            return element;

        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to create a new element", e);
        }
    }

    @Override
    public int deleteElement(int id) throws DataAccessException {
        final String attributeSql = "DELETE FROM `attribute` where element_id = :id;";

        final String elementSql = "DELETE FROM element where element_id = :id;";

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
    public List<Attribute> getAttributesForElement(int elementId) throws DataAccessException {
        final String sql = """
                SELECT * FROM `attribute` WHERE element_id = :id;
                """;
        try{
            return client.sql(sql)
                    .param("id", elementId)
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
                SELECT * FROM `attribute`
                WHERE element_id = :element_id
                AND `key` = :key;
                """;

        try{
            return client.sql(sql)
                    .param("element_id", elementId)
                    .param("key", key)
                    .query(new AttributeRowMapper())
                    .optional().orElse(null);

        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to fetch an attribute by key", e);
        }
    }

    @Override
    public Attribute createAttribute(Attribute attribute) throws DataAccessException {
        final String sql = """
                insert into attribute (element_id, `key`, value) values
                (:element_id, :key, :value);
                """;

        try{
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            client.sql(sql)
                    .param("element_id", attribute.getElementId())
                    .param("key", attribute.getKey())
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
                from element e
                INNER JOIN document d on d.document_id = e.document_id
                INNER JOIN directory dir on dir.directory_id = d.directory_id
                INNER JOIN account acc on acc.account_id = dir.account_id
                WHERE e.element_id = :id;
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
                INNER JOIN element e on a.element_id = e.element_id
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
