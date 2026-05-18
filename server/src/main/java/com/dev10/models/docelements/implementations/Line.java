package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;

public class Line extends DocumentElement {
    private Attribute startPositionX;
    private Attribute startPositionY;
    private Attribute endPositionX;
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

    public void editStartPositionX(Integer startPositionX) {
        if(startPositionX == null){
            System.out.println("Must init Line.startXPosition");
            return;
        }

        String jsonFormattedValue = Attribute.formatAsJson("startXPos", startPositionX);
        this.startPositionX.setValue(jsonFormattedValue);
    }

    public void editStartPositionY(Integer startPositionY) {
        if(startPositionY == null){
            System.out.println("Must init Line.startYPosition");
            return;
        }

        String jsonFormattedValue = Attribute.formatAsJson("startYPos", startPositionY);
        this.startPositionY.setValue(jsonFormattedValue);
    }

    public void editEndPositionX(Integer endPositionX) {
        if(startPositionX == null){
            System.out.println("Must init Line.endXPosition");
            return;
        }

        this.endPositionX.setValue(endPositionX.toString());
    }

    public void editEndPositionY(Integer endPositionY) {
        if(endPositionY == null){
            System.out.println("Must init Line.EndYPosition");
            return;
        }

        String jsonFormattedValue = Attribute.formatAsJson("endYPos", endPositionY);
        this.endPositionY.setValue(jsonFormattedValue);
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
