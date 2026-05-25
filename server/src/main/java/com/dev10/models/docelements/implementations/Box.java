package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Box extends Element {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute xPosition;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute yPosition;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute width;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute height;

    public Attribute getxPosition() {
        if(xPosition == null){
            Attribute attribute = new Attribute();
            attribute.setValue("ERROR: uninitialized Box attributes");
            return attribute;
        }

        return xPosition;
    }

    public Attribute getyPosition() {
        if(yPosition == null){
            Attribute attribute = new Attribute();
            attribute.setValue("ERROR: uninitialized Box attributes");
            return attribute;
        }

        return yPosition;
    }

    public Attribute getWidth() {
        if(width == null){
            Attribute attribute = new Attribute();
            attribute.setValue("ERROR: uninitialized Box attributes");
            return attribute;
        }

        return width;
    }

    public Attribute getHeight() {
        if(height == null){
            Attribute attribute = new Attribute();
            attribute.setValue("ERROR: uninitialized Box attributes");
            return attribute;
        }

        return height;
    }

    public void editXPosition(Integer value){
        if(xPosition == null){
            System.out.println("Must init Box.xPosition");
            return;
        }

        this.xPosition.setKey("xPos");
        this.xPosition.setValue(value.toString());
    }

    public void editYPosition(Integer value){
        if(yPosition == null){
            System.out.println("Must init Box.yPosition");
            return;
        }

        this.yPosition.setKey("yPos");
        this.yPosition.setValue(value.toString());
    }

    public void editWidth(Integer value){
        if(width == null){
            System.out.println("Must init Box.width");
            return;
        }

        this.width.setKey("width");
        this.width.setValue(value.toString());
    }

    public void editHeight(Integer value){
        if(height == null){
            System.out.println("Must init Box.height");
            return;
        }

        this.height.setKey("height");
        this.height.setValue(value.toString());
    }

    public void setxPosition(Attribute xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(Attribute yPosition) {
        this.yPosition = yPosition;
    }

    public void setWidth(Attribute width) {
        this.width = width;
    }

    public void setHeight(Attribute height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
