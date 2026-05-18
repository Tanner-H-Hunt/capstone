package com.dev10.models.mappers;

import com.dev10.models.docelements.DocumentElement;
import com.dev10.models.docelements.DocumentElementType;
import com.dev10.models.docelements.implementations.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentElementRowMapper implements RowMapper<DocumentElement> {
    @Override
    public DocumentElement mapRow(ResultSet rs, int rowNum) throws SQLException {
        DocumentElement element;
        DocumentElementType docType = DocumentElementType.valueOf(rs.getString("documentElementType"));
        switch (docType){
            case BOX -> element = new Box();
            case ARROW -> element = new Arrow();
            case TEXT -> element = new Text();
            case LINE -> element = new Line();
            case CLASS_BOX -> element = new ClassBox();
            case TODO -> element = new Todo();
            case INTERFACE -> element = new InterfaceBox();
            case TODO_GROUP -> element = new TodoGroup();
            default -> element = new Box();
        }

        element.setDocumentElementType(docType);
        element.setDocumentElementId(rs.getInt("documentElementId"));
        element.setDocumentId(rs.getInt("documentId"));

        return element;
    }
}
