package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import com.dev10.models.mappers.DocumentElementRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DocumentElementJdbcClientRepository implements DocumentElementRepository{
    @Autowired
    JdbcClient client;

    @Override
    public DocumentElement create(DocumentElement documentElement) {
        return null;
    }

    @Override
    public Attribute createAttribute(Attribute attribute) {
        return null;
    }

    @Override
    public List<DocumentElement> getElementsForDocument(int id) throws DataAccessException {
        final String sql = """
                SELECT
                    de.document_element_id as documentElementId,
                    det.`type` as documentElementType,
                    de.document_id as documentId
                FROM document_element de
                INNER JOIN element_type det ON de.element_type_id = det.element_type_id
                WHERE de.document_id = :id;
                """;

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
    public DocumentElement getElementById(int id) {
        return null;
    }

    @Override
    public Attribute getAttributeByJsonKey(String key) {
        return null;
    }

    @Override
    public boolean editElementAttribute(Attribute attribute) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
