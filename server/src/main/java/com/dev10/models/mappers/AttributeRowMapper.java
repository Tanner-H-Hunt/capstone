package com.dev10.models.mappers;

import com.dev10.models.docelements.Attribute;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttributeRowMapper implements RowMapper<Attribute> {
    @Override
    public Attribute mapRow(ResultSet rs, int rowNum) throws SQLException {
        Attribute attribute = new Attribute();

        attribute.setAttributeId(rs.getInt("attribute_id"));
        attribute.setValue(rs.getString("value"));
        attribute.setKey(rs.getString("key"));
        attribute.setElementId(rs.getInt("element_id"));

        return attribute;
    }
}
