package com.dev10.models.mappers;

import com.dev10.models.docelements.Element;
import com.dev10.models.docelements.ElementType;
import com.dev10.models.docelements.implementations.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ElementRowMapper implements RowMapper<Element> {
    @Override
    public Element mapRow(ResultSet rs, int rowNum) throws SQLException {
        Element element;
        ElementType docType = ElementType.valueOf(rs.getString("elementType"));
        switch (docType){
            case BOX -> element = new Box();
            case ARROW -> element = new Arrow();
            case TEXT -> element = new Text();
            case LINE -> element = new Line();
            case CLASS_BOX -> element = new ClassBox();
            case TODO -> element = new Todo();
            case INTERFACE -> element = new InterfaceBox();
            case TODO_GROUP -> element = new TodoGroup();
            case NOTE -> element = new Note();
            default -> element = null;
        }

        element.setElementType(docType);
        element.setElementId(rs.getInt("elementId"));
        element.setDocumentId(rs.getInt("documentId"));

        return element;
    }
}
