package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import com.dev10.repositories.DocumentElementRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Box extends DocumentElement {
    private Attribute xPosition;
    private Attribute yPosition;
    private Attribute width;
    private Attribute height;

    private final DocumentElementRepository repository;

    public Box(DocumentElementRepository repository){
        this.repository = repository;
    }

    public Attribute getxPosition() {
        return xPosition;
    }

    public void setxPosition(Integer xPosition) {
        this.xPosition.setValue(xPosition.toString());
    }

    public Attribute getyPosition() {
        return yPosition;
    }

    public void setyPosition(Integer yPosition) {
        this.yPosition.setValue(yPosition.toString());
    }

    public Attribute getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.xPosition.setValue(width.toString());
    }

    public Attribute getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height.setValue(height.toString());
    }

    @Override
    public void init() {
        Attribute defaultX = new Attribute();
        defaultX.setValue("0");
        defaultX.setDocumentElementId(this.getDocumentElementId());
        defaultX = repository.createAttribute(defaultX);

        Attribute defaultY = new Attribute();
        defaultY.setValue("0");
        defaultY.setDocumentElementId(this.getDocumentElementId());
        defaultY = repository.createAttribute(defaultY);

        Attribute defaultWidth = new Attribute();
        defaultWidth.setValue("2");
        defaultWidth.setDocumentElementId(this.getDocumentElementId());
        defaultWidth = repository.createAttribute(defaultWidth);

        Attribute defaultHeight = new Attribute();
        defaultHeight.setValue("2");
        defaultHeight.setDocumentElementId(this.getDocumentElementId());
        defaultHeight = repository.createAttribute(defaultHeight);

        this.xPosition = defaultX;
        this.yPosition = defaultY;
        this.width = defaultWidth;
        this.height = defaultHeight;
        this.attributes = List.of(xPosition, yPosition, width, height);
    }
}
