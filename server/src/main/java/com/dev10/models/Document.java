package com.dev10.models;

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
}
