package com.dev10.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class Document {
    @Min(value = 0, message = "Cannot modify the ID of the document")
    private int id;

    @NotNull(message = "document type cannot be null")
    private DocumentType documentType;

    @NotNull(message = "document name cannot be null")
    @NotEmpty(message = "document name cannot be empty")
    private String name;

    @Min(value = 0, message = "Parent directory must point to a valid directory ID")
    private int parentDirectoryId;


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"documentType\": \"" + documentType + "\"" +
                ", \"name\": \"" + name + "\"" +
                ", \"parentDirectoryId\": " + parentDirectoryId +
                '}';
    }
}
