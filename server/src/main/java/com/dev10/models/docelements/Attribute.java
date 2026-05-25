package com.dev10.models.docelements;

import java.util.Objects;

public class Attribute {
    private int attributeId;
    private int elementId;
    private String key;
    private String value;

    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }

    public int getElementId() {
        return elementId;
    }

    public void setElementId(int elementId) {
        this.elementId = elementId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
        return getAttributeId() == attribute.getAttributeId() && getElementId() == attribute.getElementId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttributeId(), getElementId());
    }

    @Override
    public String toString() {
        if(getValue() != null && getKey() != null) {
            return "{" +
                    "\"attributeId\": " + attributeId +
                    ", \"elementId\": " + elementId +
                    ", \"key\": \"" + getKey() + "\", " +
                    "\"value\": \"" + getValue() + "\" " +
                    "}";
        } else{
            return String.format("Attribute: id %s, document %s, UNINITIALIZED VALUE", attributeId, elementId);
        }
    }
}
