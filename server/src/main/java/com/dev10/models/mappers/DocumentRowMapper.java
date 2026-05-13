package com.dev10.models.mappers;

import com.dev10.models.Document;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentRowMapper implements RowMapper<Document> {
    @Override
    public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
