package com.dev10.models.docelements;

import com.dev10.repositories.DocumentElementRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

public abstract class DocumentElement {
    private int documentElementId;
    private DocumentElementType documentElementType;
    private int documentId;

    public int getDocumentElementId() {
        return documentElementId;
    }

    public void setDocumentElementId(int documentElementId) {
        this.documentElementId = documentElementId;
    }

    public DocumentElementType getDocumentElementType() {
        return documentElementType;
    }

    public void setDocumentElementType(DocumentElementType documentElementType) {
        this.documentElementType = documentElementType;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public abstract void generateDefault();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DocumentElement that = (DocumentElement) o;
        return getDocumentElementId() == that.getDocumentElementId()
                && getDocumentId() == that.getDocumentId()
                && getDocumentElementType() == that.getDocumentElementType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDocumentElementId(), getDocumentElementType(), getDocumentId());
    }
}
