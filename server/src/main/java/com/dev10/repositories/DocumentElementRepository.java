package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;

import java.util.List;

public interface DocumentElementRepository {
    DocumentElement create(DocumentElement documentElement);
    boolean delete(int id);
    boolean editElementAttribute(Attribute attribute);
    Attribute createAttribute(Attribute attribute);
    List<DocumentElement> getElementsForDocument(int id) throws DataAccessException;
    DocumentElement getElementById(int id);
    Attribute getAttributeByJsonKey(String key);

}
