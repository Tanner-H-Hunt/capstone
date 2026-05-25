package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Text extends Element {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected Attribute xPosition;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected Attribute yPosition;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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

        this.xPosition.setKey("xPos");
        this.xPosition.setValue(value.toString());
    }

    public void editYPosition(Integer value){
        if(yPosition == null){
            System.out.println("Must init Text.yPosition");
            return;
        }

        this.yPosition.setKey("yPos");
        this.yPosition.setValue(value.toString());
    }

    public void editInnerText(String value){
        if(innerText == null){
            System.out.println("Must init Text.innerText");
            return;
        }

        this.innerText.setKey("innerText");
        this.innerText.setValue(value);
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
