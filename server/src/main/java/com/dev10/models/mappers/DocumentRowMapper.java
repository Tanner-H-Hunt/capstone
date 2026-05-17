package com.dev10.models.mappers;

import com.dev10.models.Document;
import com.dev10.models.DocumentType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentRowMapper implements RowMapper<Document> {
    @Override
    public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
        Document document = new Document();
        DocumentType docType = DocumentType.valueOf(rs.getString("document_type_name"));

        document.setId(rs.getInt("document_id"));
        document.setDocumentType(docType);
        document.setName(rs.getString("document_name"));
        document.setParentDirectoryId(rs.getInt("directory_id"));

        return document;
    }
}
