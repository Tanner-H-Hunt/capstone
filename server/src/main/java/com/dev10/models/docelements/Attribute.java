package com.dev10.models.docelements;

import java.util.Objects;

public class Attribute {
    private int attributeId;
    private int documentElementId;
    private String value;

    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }

    public int getDocumentElementId() {
        return documentElementId;
    }

    public void setDocumentElementId(int documentElementId) {
        this.documentElementId = documentElementId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        return getAttributeId() == attribute.getAttributeId() && getDocumentElementId() == attribute.getDocumentElementId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttributeId(), getDocumentElementId());
    }
}
