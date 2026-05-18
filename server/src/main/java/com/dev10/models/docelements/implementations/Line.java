package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import com.dev10.repositories.DocumentElementRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Line extends DocumentElement {
    private Attribute startPositionX;
    private Attribute startPositionY;
    private Attribute endPositionX;
    private Attribute endPositionY;

    private final DocumentElementRepository repository;

    public Line(DocumentElementRepository repository){
        this.repository = repository;
    }

    public Attribute getStartPositionX() {
        return startPositionX;
    }

    public void setStartPositionX(Integer startPositionX) {
        this.startPositionX.setValue(startPositionX.toString());
    }

    public Attribute getStartPositionY() {
        return startPositionY;
    }

    public void setStartPositionY(Integer startPositionY) {
        this.startPositionY.setValue(startPositionY.toString());
    }

    public Attribute getEndPositionX() {
        return endPositionX;
    }

    public void setEndPositionX(Integer endPositionX) {
        this.endPositionX.setValue(endPositionX.toString());
    }

    public Attribute getEndPositionY() {
        return endPositionY;
    }

    public void setEndPositionY(Integer endPositionY) {
        this.endPositionY.setValue(endPositionY.toString());
    }

    @Override
    public void init() {
        Attribute defaultStartPositionX = new Attribute();
        defaultStartPositionX.setValue("0");
        defaultStartPositionX.setDocumentElementId(this.getDocumentElementId());
        defaultStartPositionX = repository.createAttribute(defaultStartPositionX);

        Attribute defaultStartPositionY = new Attribute();
        defaultStartPositionY.setValue("0");
        defaultStartPositionY.setDocumentElementId(this.getDocumentElementId());
        defaultStartPositionY = repository.createAttribute(defaultStartPositionY);

        Attribute defaultEndPositionX = new Attribute();
        defaultEndPositionX.setValue("1");
        defaultEndPositionX.setDocumentElementId(this.getDocumentElementId());
        defaultEndPositionX = repository.createAttribute(defaultEndPositionX);

        Attribute defaultEndPositionY = new Attribute();
        defaultEndPositionY.setValue("0");
        defaultEndPositionY.setDocumentElementId(this.getDocumentElementId());
        defaultEndPositionY = repository.createAttribute(defaultEndPositionY);

        startPositionX = defaultStartPositionX;
        startPositionY = defaultStartPositionY;
        endPositionX = defaultEndPositionX;
        endPositionY = defaultEndPositionY;
        this.attributes = List.of(startPositionX, startPositionY, endPositionX, endPositionY);
    }
}
