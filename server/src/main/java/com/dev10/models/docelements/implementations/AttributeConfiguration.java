package com.dev10.models.docelements.implementations;

import com.dev10.models.DataAccessException;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import com.dev10.repositories.DocumentElementRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttributeConfiguration {
    private final DocumentElementRepository repository;

    public AttributeConfiguration(DocumentElementRepository repository){
        this.repository = repository;
    }

    public void initAttributes(DocumentElement element) throws DataAccessException {
        switch (element.getDocumentElementType()){
            case BOX:
                initBox((Box) element);
                break;
            case LINE:
                initLine((Line) element);
                break;
            case TEXT:
                initText((Text) element);
                break;
            case CLASS_BOX:
                initClassBox((ClassBox) element);
                break;
            case INTERFACE:
                initInterface((InterfaceBox) element);
                break;
            case ARROW:
                initArrow((Arrow) element);
                break;
            case TODO:
                initTodo((Todo) element);
                break;
            case TODO_GROUP:
                initTodoGroup((TodoGroup) element);
                break;
        }
    }

    private void initBox(Box box) throws DataAccessException {
        Attribute defaultX = new Attribute();
        Attribute defaultY = new Attribute();
        Attribute defaultWidth = new Attribute();
        Attribute defaultHeight = new Attribute();

        // attach the attributes to the box
        box.setxPosition(defaultX);
        box.setyPosition(defaultY);
        box.setWidth(defaultWidth);
        box.setHeight(defaultHeight);

        // modify the attribute values
        defaultX.setDocumentElementId(box.getDocumentElementId());
        defaultY.setDocumentElementId(box.getDocumentElementId());
        defaultWidth.setDocumentElementId(box.getDocumentElementId());
        defaultHeight.setDocumentElementId(box.getDocumentElementId());

        box.editXPosition(0);
        box.editYPosition(0);
        box.editWidth(2);
        box.editHeight(2);

        // send to the database for an id
        int xId = repository.createAttribute(defaultX).getAttributeId();
        int yId = repository.createAttribute(defaultY).getAttributeId();
        int widthId = repository.createAttribute(defaultWidth).getAttributeId();
        int heightId = repository.createAttribute(defaultHeight).getAttributeId();

        // attach the ID to the attributes
        defaultX.setAttributeId(xId);
        defaultY.setAttributeId(yId);
        defaultWidth.setAttributeId(widthId);
        defaultHeight.setAttributeId(heightId);

        // enable the list of attributes on this object
        box.setAttributes(List.of(
                box.getxPosition(),
                box.getyPosition(),
                box.getWidth(),
                box.getHeight()
        ));

    }

    private void initLine(Line line) throws DataAccessException {
        Attribute defaultStartPositionX = new Attribute();
        Attribute defaultStartPositionY = new Attribute();
        Attribute defaultEndPositionX = new Attribute();
        Attribute defaultEndPositionY = new Attribute();

        // attach the initial attributes to the element for editing (avoids null ptrs)
        line.setStartPositionX(defaultStartPositionX);
        line.setStartPositionY(defaultStartPositionY);
        line.setEndPositionX(defaultEndPositionX);
        line.setEndPositionY(defaultEndPositionY);

        // modify the attribute values
        defaultStartPositionX.setDocumentElementId(line.getDocumentElementId());
        defaultStartPositionY.setDocumentElementId(line.getDocumentElementId());
        defaultEndPositionX.setDocumentElementId(line.getDocumentElementId());
        defaultEndPositionY.setDocumentElementId(line.getDocumentElementId());

        line.editStartPositionX(0);
        line.editStartPositionY(0);
        line.editEndPositionX(0);
        line.editEndPositionY(0);

        // send the attributes to the database for an id
        int xStartId = repository.createAttribute(defaultStartPositionX).getAttributeId();
        int yStartId = repository.createAttribute(defaultStartPositionY).getAttributeId();
        int xEndId = repository.createAttribute(defaultEndPositionX).getAttributeId();
        int yEndId = repository.createAttribute(defaultEndPositionY).getAttributeId();

        // update the attributes with their new ID's
        defaultStartPositionX.setAttributeId(xStartId);
        defaultStartPositionY.setAttributeId(yStartId);
        defaultEndPositionX.setAttributeId(xEndId);
        defaultEndPositionY.setAttributeId(yEndId);

        // enable the list of attributes on this object
        line.setAttributes( List.of(
                line.getStartPositionX(),
                line.getStartPositionY(),
                line.getEndPositionX(),
                line.getEndPositionY()));
    }

    private void initText(Text text) throws DataAccessException {
        // init the attributes
        Attribute defaultXPos = new Attribute();
        Attribute defaultYPos = new Attribute();
        Attribute defaultInnerText = new Attribute();

        // mount the attributes to the element early (prevents nullptrs)
        text.setxPosition(defaultXPos);
        text.setyPosition(defaultYPos);
        text.setInnerText(defaultInnerText);

        // modify the attribute values
        text.editXPosition(0);
        text.editYPosition(0);
        text.editInnerText("text");

        // set the attributes parent document id
        defaultXPos.setDocumentElementId(text.getDocumentElementId());
        defaultYPos.setDocumentElementId(text.getDocumentElementId());
        defaultInnerText.setDocumentElementId(text.getDocumentElementId());

        // send the attributes to the database for their ID
        int xId = repository.createAttribute(defaultXPos).getAttributeId();
        int yId = repository.createAttribute(defaultYPos).getAttributeId();
        int innerTextId = repository.createAttribute(defaultInnerText).getAttributeId();

        // attach the ID's to the attributes
        defaultXPos.setAttributeId(xId);
        defaultYPos.setAttributeId(yId);
        defaultInnerText.setAttributeId(innerTextId);

        // enable the list of attributes on this object
        text.setAttributes(List.of(
                text.getxPosition(),
                text.getyPosition(),
                text.getInnerText()
        ));
    }

    private void initClassBox(ClassBox classBox){
        // init the attributes

        // mount the attributes to the element early (prevents nullptrs)

        // modify the attribute values

        // set the attributes parent document id

        // send the attributes to the database for their ID

        // attach the ID's to the attributes

        // enable the list of attributes on this object
    }

    private void initInterface(InterfaceBox interfaceBox){
        // init the attributes

        // mount the attributes to the element early (prevents nullptrs)

        // modify the attribute values

        // set the attributes parent document id

        // send the attributes to the database for their ID

        // attach the ID's to the attributes

        // enable the list of attributes on this object
    }

    private void initArrow(Arrow arrow){
        // init the attributes

        // mount the attributes to the element early (prevents nullptrs)

        // modify the attribute values

        // set the attributes parent document id

        // send the attributes to the database for their ID

        // attach the ID's to the attributes

        // enable the list of attributes on this object
    }

    private void initTodoGroup(TodoGroup todoGroup){
        // init the attributes

        // mount the attributes to the element early (prevents nullptrs)

        // modify the attribute values

        // set the attributes parent document id

        // send the attributes to the database for their ID

        // attach the ID's to the attributes

        // enable the list of attributes on this object
    }

    private void initTodo(Todo todo){
        // init the attributes

        // mount the attributes to the element early (prevents nullptrs)

        // modify the attribute values

        // set the attributes parent document id

        // send the attributes to the database for their ID

        // attach the ID's to the attributes

        // enable the list of attributes on this object
    }

}
