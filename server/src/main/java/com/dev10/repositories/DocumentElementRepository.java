package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;

import java.util.List;

public interface DocumentElementRepository {
    List<DocumentElement> getElementsForDocument(int id) throws DataAccessException;
    DocumentElement getElementById(int id) throws DataAccessException;
    DocumentElement createElement(DocumentElement documentElement) throws DataAccessException;
    int deleteElement(int id) throws DataAccessException;

    List<Attribute> getAttributesForElement(int documentElementId) throws DataAccessException;
    Attribute getAttributeById(int id) throws DataAccessException;
    Attribute getAttributeByJsonKey(int elementId, String key) throws DataAccessException;
    Attribute createAttribute(Attribute attribute) throws DataAccessException;
    boolean editAttribute(Attribute attribute) throws DataAccessException;
}
