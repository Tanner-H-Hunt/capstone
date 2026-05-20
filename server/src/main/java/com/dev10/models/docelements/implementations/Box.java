package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import com.dev10.models.docelements.DocumentElementType;
import com.dev10.repositories.DocumentElementRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;
import org.w3c.dom.Attr;

import java.util.List;


public class Box extends DocumentElement {
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

        String jsonFormattedValue = Attribute.formatAsJson("xPos", value);
        this.xPosition.setValue(jsonFormattedValue);
    }

    public void editYPosition(Integer value){
        if(yPosition == null){
            System.out.println("Must init Box.yPosition");
            return;
        }

        String jsonFormattedValue = Attribute.formatAsJson("yPos", value);
        this.yPosition.setValue(jsonFormattedValue);
    }

    public void editWidth(Integer value){
        if(width == null){
            System.out.println("Must init Box.width");
            return;
        }

        String jsonFormattedValue = Attribute.formatAsJson("width", value);
        this.width.setValue(jsonFormattedValue);
    }

    public void editHeight(Integer value){
        if(height == null){
            System.out.println("Must init Box.height");
            return;
        }

        String jsonFormattedValue = Attribute.formatAsJson("height", value);
        this.height.setValue(jsonFormattedValue);
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
