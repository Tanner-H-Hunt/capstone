package com.dev10.models;

import java.util.Objects;

public class Document {
    private int id;
    private DocumentType documentType;
    private String name;
    private int parentDirectoryId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentDirectoryId() {
        return parentDirectoryId;
    }

    public void setParentDirectoryId(int parentDirectoryId) {
        this.parentDirectoryId = parentDirectoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;

        return getId() == document.getId()
                && getParentDirectoryId() == document.getParentDirectoryId()
                && getDocumentType() == document.getDocumentType()
                && Objects.equals(getName(), document.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDocumentType(), getName(), getParentDirectoryId());
    }
}
