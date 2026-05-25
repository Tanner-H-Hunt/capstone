package com.dev10.domain;

import com.dev10.models.DataAccessException;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import com.dev10.models.docelements.ElementType;
import com.dev10.models.docelements.implementations.*;
import com.dev10.repositories.ElementRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttributeConfigurationService {
    private final ElementRepository repository;

    public AttributeConfigurationService(ElementRepository repository){
        this.repository = repository;
    }

    public Element initAttributes(Element element) throws DataAccessException {
        return switch (element.getElementType()) {
            case BOX -> initBox(element);
            case LINE -> initLine(element);
            case TEXT -> initText(element);
            case CLASS_BOX -> initClassBox(element);
            case INTERFACE -> initInterface(element);
            case ARROW -> initArrow(element);
            case TODO -> initTodo(element);
            case TODO_GROUP -> initTodoGroup(element);
            case NOTE -> initNote(element);
        };
    }

    private Box initBox(Element element) throws DataAccessException {
        Box box = new Box();
        box.setElementType(ElementType.BOX);
        box.setDocumentId(element.getDocumentId());
        box.setElementId(element.getElementId());

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
        defaultX.setElementId(box.getElementId());
        defaultY.setElementId(box.getElementId());
        defaultWidth.setElementId(box.getElementId());
        defaultHeight.setElementId(box.getElementId());

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

    private Line initLine(Element element) throws DataAccessException {
        Line line = new Line();
        line.setElementType(ElementType.LINE);
        line.setElementId(element.getElementId());
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
        defaultStartPositionX.setElementId(line.getElementId());
        defaultStartPositionY.setElementId(line.getElementId());
        defaultEndPositionX.setElementId(line.getElementId());
        defaultEndPositionY.setElementId(line.getElementId());

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

    private Text initText(Element element) throws DataAccessException {
        Text text = new Text();
        text.setElementType(ElementType.TEXT);
        text.setElementId(element.getElementId());
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
        defaultXPos.setElementId(text.getElementId());
        defaultYPos.setElementId(text.getElementId());
        defaultInnerText.setElementId(text.getElementId());

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

    private ClassBox initClassBox(Element element){
        ClassBox classBox = new ClassBox();
        classBox.setElementType(ElementType.CLASS_BOX);
        classBox.setElementId(element.getElementId());
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

    private InterfaceBox initInterface(Element element){
        InterfaceBox interfaceBox = new InterfaceBox();
        interfaceBox.setElementType(ElementType.INTERFACE);
        interfaceBox.setElementId(element.getElementId());
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

    private Arrow initArrow(Element element){
        Arrow arrow = new Arrow();
        arrow.setElementType(ElementType.ARROW);
        arrow.setElementId(element.getElementId());
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

    private TodoGroup initTodoGroup(Element element){
        TodoGroup todoGroup = new TodoGroup();
        todoGroup.setElementType(ElementType.TODO_GROUP);
        todoGroup.setElementId(element.getElementId());
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

    private Todo initTodo(Element element){
        Todo todo = new Todo();
        todo.setElementType(ElementType.TODO);
        todo.setElementId(element.getElementId());
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

    private Note initNote(Element element) throws DataAccessException {
        Note note = new Note();
        note.setElementType(ElementType.NOTE);
        note.setElementId(element.getElementId());
        note.setDocumentId(element.getDocumentId());

        // init the attributes
        Attribute innerText = new Attribute();
        Attribute order = new Attribute();

        // mount the attributes to the element early (prevents nullptrs)
        note.setInnerText(innerText);
        note.setOrder(order);

        // modify the attribute values
        note.editInnerText("This is a text editor for a note");
        note.editOrder(0);

        // set the attributes parent element id
        innerText.setElementId(element.getElementId());
        order.setElementId(element.getElementId());

        // send the attributes to the database for their ID
        int innerTextId = repository.createAttribute(innerText).getAttributeId();
        int orderId = repository.createAttribute(order).getAttributeId();

        // attach the ID's to the attributes
        innerText.setAttributeId(innerTextId);
        order.setAttributeId(orderId);

        // enable the list of attributes on this object
        note.setAttributes(List.of(innerText, order));

        return  note;
    }
}
