package com.dev10.repositories;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;

public interface DocumentElementRepository {
    DocumentElement create(DocumentElement documentElement);
    boolean delete(int id);
    boolean editElementAttribute(Attribute attribute);
    Attribute createAttribute(Attribute attribute);
}
