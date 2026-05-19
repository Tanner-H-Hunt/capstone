package com.dev10.models.docelements.implementations;

import com.dev10.models.DataAccessException;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import com.dev10.models.docelements.DocumentElementType;
import com.dev10.repositories.DocumentElementRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttributeConfiguration {
    private final DocumentElementRepository repository;

    public AttributeConfiguration(DocumentElementRepository repository){
        this.repository = repository;
    }

    public DocumentElement initAttributes(DocumentElement element) throws DataAccessException {
        return switch (element.getDocumentElementType()) {
            case BOX -> initBox(element);
            case LINE -> initLine(element);
            case TEXT -> initText(element);
            case CLASS_BOX -> initClassBox(element);
            case INTERFACE -> initInterface(element);
            case ARROW -> initArrow(element);
            case TODO -> initTodo(element);
            case TODO_GROUP -> initTodoGroup(element);
        };
    }

    private Box initBox(DocumentElement element) throws DataAccessException {
        Box box = new Box();
        box.setDocumentElementType(DocumentElementType.BOX);
        box.setDocumentId(element.getDocumentId());
        box.setDocumentElementId(element.getDocumentElementId());

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

        return box;
    }

    private Line initLine(DocumentElement element) throws DataAccessException {
        Line line = new Line();
        line.setDocumentElementType(DocumentElementType.LINE);
        line.setDocumentElementId(element.getDocumentElementId());
        line.setDocumentId(element.getDocumentId());

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
        line.editEndPositionX(1);
        line.editEndPositionY(1);

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

        return line;
    }

    private Text initText(DocumentElement element) throws DataAccessException {
        Text text = new Text();
        text.setDocumentElementType(DocumentElementType.TEXT);
        text.setDocumentElementId(element.getDocumentElementId());
        text.setDocumentId(element.getDocumentId());

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

        return text;
    }

    private ClassBox initClassBox(DocumentElement element){
        ClassBox classBox = new ClassBox();
        classBox.setDocumentElementType(DocumentElementType.CLASS_BOX);
        classBox.setDocumentElementId(element.getDocumentElementId());
        classBox.setDocumentId(element.getDocumentId());

        // init the attributes

        // mount the attributes to the element early (prevents nullptrs)

        // modify the attribute values

        // set the attributes parent document id

        // send the attributes to the database for their ID

        // attach the ID's to the attributes

        // enable the list of attributes on this object

        return classBox;
    }

    private InterfaceBox initInterface(DocumentElement element){
        InterfaceBox interfaceBox = new InterfaceBox();
        interfaceBox.setDocumentElementType(DocumentElementType.INTERFACE);
        interfaceBox.setDocumentElementId(element.getDocumentElementId());
        interfaceBox.setDocumentId(element.getDocumentId());

        // init the attributes

        // mount the attributes to the element early (prevents nullptrs)

        // modify the attribute values

        // set the attributes parent document id

        // send the attributes to the database for their ID

        // attach the ID's to the attributes

        // enable the list of attributes on this object

        return  interfaceBox;
    }

    private Arrow initArrow(DocumentElement element){
        Arrow arrow = new Arrow();
        arrow.setDocumentElementType(DocumentElementType.ARROW);
        arrow.setDocumentElementId(element.getDocumentElementId());
        arrow.setDocumentId(element.getDocumentId());

        // init the attributes

        // mount the attributes to the element early (prevents nullptrs)

        // modify the attribute values

        // set the attributes parent document id

        // send the attributes to the database for their ID

        // attach the ID's to the attributes

        // enable the list of attributes on this object

        return arrow;
    }

    private TodoGroup initTodoGroup(DocumentElement element){
        TodoGroup todoGroup = new TodoGroup();
        todoGroup.setDocumentElementType(DocumentElementType.TODO_GROUP);
        todoGroup.setDocumentElementId(element.getDocumentElementId());
        todoGroup.setDocumentId(element.getDocumentId());

        // init the attributes

        // mount the attributes to the element early (prevents nullptrs)

        // modify the attribute values

        // set the attributes parent document id

        // send the attributes to the database for their ID

        // attach the ID's to the attributes

        // enable the list of attributes on this object

        return  todoGroup;
    }

    private Todo initTodo(DocumentElement element){
        Todo todo = new Todo();
        todo.setDocumentElementType(DocumentElementType.TODO);
        todo.setDocumentElementId(element.getDocumentElementId());
        todo.setDocumentId(element.getDocumentId());

        // init the attributes

        // mount the attributes to the element early (prevents nullptrs)

        // modify the attribute values

        // set the attributes parent document id

        // send the attributes to the database for their ID

        // attach the ID's to the attributes

        // enable the list of attributes on this object

        return  todo;
    }

}
