package com.dev10.models.docelements;

import java.util.List;
import java.util.Objects;

public abstract class DocumentElement {
    private int documentElementId;
    private DocumentElementType documentElementType;
    private int documentId;
    protected List<Attribute> attributes;

    public List<Attribute> getAttributes(){
        if(attributes == null){
            return List.of();
        }
        return attributes;
    }

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

    public void setAttributes(List<Attribute> attributes){
        this.attributes = attributes;
    }

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

    @Override
    public String toString() {
        return "{" +
                "\"documentElementId\": " + documentElementId +
                ", \"documentElementType\": \"" + documentElementType + "\"" +
                ", \"documentId\": " + documentId +
                ", \"attributes\": [" + attributeListToString() +
                "]}";
    }

    private String attributeListToString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < getAttributes().size(); i++){
            sb.append(getAttributes().get(i).toString());
            if(i != getAttributes().size() - 1){
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
