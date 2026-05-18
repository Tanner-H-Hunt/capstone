package com.dev10.repositories;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentElementJdbcClientRepository implements DocumentElementRepository{
    @Override
    public DocumentElement create(DocumentElement documentElement) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Attribute createAttribute(Attribute attribute) {
        return null;
    }

    @Override
    public boolean editElementAttribute(Attribute attribute) {
        return false;
    }
}
