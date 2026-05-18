package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;


public class Text extends DocumentElement {
    protected Attribute xPosition;
    protected Attribute yPosition;
    protected Attribute innerText;

    public Attribute getxPosition() {
        if(xPosition == null){
            Attribute attribute = new Attribute();
            attribute.setValue("Must init Text.xPosition");
            return attribute;
        }

        return xPosition;
    }

    public Attribute getyPosition() {
        if(yPosition == null){
            Attribute attribute = new Attribute();
            attribute.setValue("Must init Text.yPosition");
            return attribute;
        }

        return yPosition;
    }

    public Attribute getInnerText() {
        if(innerText == null){
            Attribute attribute = new Attribute();
            attribute.setValue("Must init Text.innerText");
            return attribute;
        }

        return innerText;
    }

    public void editXPosition(Integer value){
        if(xPosition == null){
            System.out.println("Must init Text.xPosition");
            return;
        }

        String json = Attribute.formatAsJson("xPos", value);
        xPosition.setValue(json);
    }

    public void editYPosition(Integer value){
        if(yPosition == null){
            System.out.println("Must init Text.yPosition");
            return;
        }

        String json = Attribute.formatAsJson("yPos", value);
        yPosition.setValue(json);
    }

    public void editInnerText(String value){
        if(innerText == null){
            System.out.println("Must init Text.innerText");
            return;
        }

        String json = Attribute.formatAsJson("innerText", value);
        innerText.setValue(json);
    }

    public void setxPosition(Attribute xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(Attribute yPosition) {
        this.yPosition = yPosition;
    }

    public void setInnerText(Attribute innerText) {
        this.innerText = innerText;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
