package com.dev10.models.mappers;

import com.dev10.models.Relationship;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RelationshipRowMapper implements RowMapper<Relationship> {
    @Override
    public Relationship mapRow(ResultSet rs, int rowNum) throws SQLException {
        Relationship relation = new Relationship();
        relation.setId(rs.getInt("relation_id"));
        relation.setDocumentId(rs.getInt("document_id"));
        relation.setElementId(rs.getInt("element_id"));
        relation.setName(rs.getString("name"));
        relation.setDescription(rs.getString("description"));

        return relation;
    }
}
