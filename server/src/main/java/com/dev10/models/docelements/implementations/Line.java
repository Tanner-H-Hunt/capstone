package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Line extends Element {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute startPositionX;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute startPositionY;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute endPositionX;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Attribute endPositionY;

    public Attribute getStartPositionX() {
        if(startPositionX == null){
            Attribute attribute = new Attribute();
            attribute.setValue("ERROR: uninitialized line attributes");
            return attribute;
        }

        return startPositionX;
    }

    public Attribute getStartPositionY() {
        if(startPositionY == null){
            Attribute attribute = new Attribute();
            attribute.setValue("ERROR: uninitialized line attributes");
            return attribute;
        }

        return startPositionY;
    }

    public Attribute getEndPositionX() {
        if(endPositionX == null){
            Attribute attribute = new Attribute();
            attribute.setValue("ERROR: uninitialized line attributes");
            return attribute;
        }

        return endPositionX;
    }

    public Attribute getEndPositionY() {
        if(endPositionY == null){
            Attribute attribute = new Attribute();
            attribute.setValue("ERROR: uninitialized line attributes");
            return attribute;
        }

        return endPositionY;
    }

    public void editStartPositionX(Integer value) {
        if(startPositionX == null){
            System.out.println("Must init Line.startXPosition");
            return;
        }

        this.startPositionX.setKey("startXPos");
        this.startPositionX.setValue(value.toString());
    }

    public void editStartPositionY(Integer value) {
        if(startPositionY == null){
            System.out.println("Must init Line.startYPosition");
            return;
        }

        this.startPositionY.setKey("startYPos");
        this.startPositionY.setValue(value.toString());
    }

    public void editEndPositionX(Integer value) {
        if(startPositionX == null){
            System.out.println("Must init Line.endXPosition");
            return;
        }

        this.endPositionX.setKey("endXPos");
        this.endPositionX.setValue(value.toString());
    }

    public void editEndPositionY(Integer value) {
        if(endPositionY == null){
            System.out.println("Must init Line.EndYPosition");
            return;
        }

        this.endPositionY.setKey("endYPos");
        this.endPositionY.setValue(value.toString());
    }

    public void setStartPositionX(Attribute startPositionX){
        this.startPositionX = startPositionX;
    }

    public void setStartPositionY(Attribute startPositionY){
        this.startPositionY = startPositionY;
    }

    public void setEndPositionX(Attribute endPositionX){
        this.endPositionX = endPositionX;
    }

    public void setEndPositionY(Attribute endPositionY){
        this.endPositionY = endPositionY;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
