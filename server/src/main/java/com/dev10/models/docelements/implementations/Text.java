package com.dev10.models.docelements.implementations;

import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import com.dev10.repositories.DocumentElementRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Text extends DocumentElement {
    private Attribute xPosition;
    private Attribute yPosition;
    private Attribute innerText;

    private final DocumentElementRepository repository;

    public Text(DocumentElementRepository repository){
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

    public Attribute getInnerText() {
        return innerText;
    }

    public void setInnerText(String innerText) {
        this.innerText.setValue(innerText);
    }

    @Override
    public void init() {
        Attribute defaultXPosition = new Attribute();
        defaultXPosition.setValue("0");
        defaultXPosition.setDocumentElementId(this.getDocumentElementId());
        defaultXPosition = repository.createAttribute(defaultXPosition);

        Attribute defaultYPosition = new Attribute();
        defaultYPosition.setValue("0");
        defaultYPosition.setDocumentElementId(this.getDocumentElementId());
        defaultYPosition = repository.createAttribute(defaultYPosition);

        Attribute defaultText = new Attribute();
        defaultText.setValue("text");
        defaultYPosition.setDocumentElementId(this.getDocumentElementId());
        defaultText = repository.createAttribute(defaultText);

        this.xPosition = defaultXPosition;
        this.yPosition = defaultYPosition;
        this.innerText = defaultText;
        this.attributes = List.of(xPosition, yPosition, innerText);
    }
}
