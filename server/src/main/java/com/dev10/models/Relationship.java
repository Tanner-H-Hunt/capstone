package com.dev10.models;

import java.util.Objects;

public class Relationship {
    private int id;
    private int documentId;
    private int elementId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getElementId() {
        return elementId;
    }

    public void setElementId(int elementId) {
        this.elementId = elementId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Relationship that = (Relationship) o;
        return getId() == that.getId() && getDocumentId() == that.getDocumentId() && getElementId() == that.getElementId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDocumentId(), getElementId());
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\": " + id +
                ", \"documentId\": " + documentId +
                ", \"documentElementId\": " + elementId +
                '}';
    }
}
