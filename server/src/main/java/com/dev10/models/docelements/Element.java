package com.dev10.models.docelements;

import java.util.List;
import java.util.Objects;

public class Element {
    private int elementId;
    private ElementType elementType;
    private int documentId;
    protected List<Attribute> attributes;

    public List<Attribute> getAttributes(){
        if(attributes == null){
            return List.of();
        }
        return attributes;
    }

    public int getElementId() {
        return elementId;
    }

    public void setElementId(int elementId) {
        this.elementId = elementId;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
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
        Element that = (Element) o;
        return getElementId() == that.getElementId()
                && getDocumentId() == that.getDocumentId()
                && getElementType() == that.getElementType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getElementId(), getElementType(), getDocumentId());
    }

    @Override
    public String toString() {
        return "{" +
                "\"elementId\": " + elementId +
                ", \"elementType\": \"" + elementType + "\"" +
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
