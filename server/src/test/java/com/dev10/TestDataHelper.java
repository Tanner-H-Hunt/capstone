package com.dev10;

import com.dev10.models.*;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import com.dev10.models.docelements.ElementType;
import com.dev10.models.docelements.implementations.Box;
import com.dev10.models.docelements.implementations.Line;
import com.dev10.models.docelements.implementations.Text;

import java.util.List;

public class TestDataHelper {

    public static List<User> getAllUsers(){
        User user1 = new User();
        User user2 = new User();

        user1.setId(1);
        user1.setEmail("a@a.com");
        user1.setPassword("a");
        user1.setSalt("test");

        user2.setId(2);
        user2.setEmail("b@b.com");
        user2.setPassword("b");
        user2.setSalt("test");

        return List.of(
                user1,
                user2
        );
    }

    public static User getUserNotInDatabase(){
        User user = new User();
        user.setId(0);
        user.setEmail("c@c.com");
        user.setPassword("c");
        user.setSalt("test");

        return user;
    }

    public static List<Document> getDocumentsForUser1(){
        Document fso1 = new Document();
        fso1.setId(1);
        fso1.setName("user1-todo");
        fso1.setDocumentType(DocumentType.TODO);
        fso1.setParentDirectoryId(1);

        Document fso2 = new Document();
        fso2.setId(2);
        fso2.setName("user1-uml");
        fso2.setDocumentType(DocumentType.UML);
        fso2.setParentDirectoryId(1);

        Document fso3 = new Document();
        fso3.setId(3);
        fso3.setName("user1-note");
        fso3.setDocumentType(DocumentType.NOTE);
        fso3.setParentDirectoryId(3);

        return List.of(
                fso1,
                fso2,
                fso3
        );
    }

    public static List<Document> getDocumentsForUser2(){
        Document fso1 = new Document();
        fso1.setId(4);
        fso1.setName("user2-note");
        fso1.setDocumentType(DocumentType.NOTE);
        fso1.setParentDirectoryId(2);

        Document fso2 = new Document();
        fso2.setId(5);
        fso2.setName("user2-todo");
        fso2.setDocumentType(DocumentType.TODO);
        fso2.setParentDirectoryId(2);

        Document fso3 = new Document();
        fso3.setId(6);
        fso3.setName("user2-uml");
        fso3.setDocumentType(DocumentType.UML);
        fso3.setParentDirectoryId(2);

        return List.of(
                fso1,
                fso2,
                fso3
        );
    }

    public static List<Directory> getDirectoriesForUser1(){
        Directory directory1 = new Directory();
        directory1.setId(1);
        directory1.setAccountId(1);
        directory1.setParentDirectoryId(0);
        directory1.setDirectoryName("root-directory");

        Directory directory2 = new Directory();
        directory2.setId(3);
        directory2.setAccountId(1);
        directory2.setParentDirectoryId(1);
        directory2.setDirectoryName("subdirectory-test");

        return List.of(directory1, directory2);
    }

    public static Document getDocumentNotInDatabase(){
        Document document = new Document();

        document.setId(0);
        document.setDocumentType(DocumentType.TODO);
        document.setName("to-be-added-to-database");
        document.setParentDirectoryId(1);

        return document;
    }

    public static Directory getDirectoryNotInDatabase(){
        Directory directory = new Directory();
        directory.setDirectoryName("TestDirectory");
        directory.setAccountId(1);
        directory.setParentDirectoryId(1);

        return directory;
    }

    public static List<Directory> getDirectoriesForUser2(){
        Directory directory1 = new Directory();
        directory1.setId(2);
        directory1.setAccountId(2);
        directory1.setParentDirectoryId(0);
        directory1.setDirectoryName("root-directory");

        Directory directory2 = new Directory();
        directory2.setId(4);
        directory2.setAccountId(2);
        directory2.setParentDirectoryId(0);
        directory2.setDirectoryName("sub-directory");

        return List.of(directory1, directory2);
    }

    public static List<Element> getElementsForUser1Uml(){
        Element line = new Line();
        line.setElementId(1);
        line.setDocumentId(2);
        line.setElementType(ElementType.LINE);

        Element box = new Box();
        box.setElementId(2);
        box.setDocumentId(2);
        box.setElementType(ElementType.BOX);

        return List.of(line, box);
    }

    public static List<Element> getElementsForUser2Note(){
        Element text = new Text();
        text.setElementType(ElementType.TEXT);
        text.setElementId(3);
        text.setDocumentId(4);

        return List.of(text);
    }

    public static Element getBoxNotInDatabase(){
        Element element = new Box();
        element.setDocumentId(2);
        element.setElementId(0);
        element.setElementType(ElementType.BOX);
        return element;
    }

    public static List<Attribute> getAttributesForElement1() {
        Attribute startX = new Attribute();
        startX.setAttributeId(1);
        startX.setElementId(1);
        startX.setKey("startXPos");
        startX.setValue("0");

        Attribute startY = new Attribute();
        startY.setAttributeId(2);
        startY.setElementId(1);
        startY.setKey("startYPos");
        startY.setValue("0");

        Attribute endX = new Attribute();
        endX.setAttributeId(3);
        endX.setElementId(1);
        endX.setKey("endXPos");
        endX.setValue("1");

        Attribute endY = new Attribute();
        endY.setAttributeId(4);
        endY.setElementId(1);
        endY.setKey("endYPos");
        endY.setValue("0");

        return List.of(startX, startY, endX, endY);
    }

    public static Attribute getAttribute1() {
        return getAttributesForElement1().get(0);
    }

    public static Attribute getAttributeNotInDatabase() {
        Attribute attribute = new Attribute();
        attribute.setAttributeId(0);
        attribute.setElementId(1);
        attribute.setKey("newAttribute");
        attribute.setValue("123");

        return attribute;
    }

    public static Attribute getUpdatedAttribute1() {
        Attribute attribute = new Attribute();
        attribute.setAttributeId(1);
        attribute.setElementId(1);
        attribute.setKey("startXPos");
        attribute.setValue("999");

        return attribute;
    }

    public static List<Relationship> getRelationshipsForElement2(){
        Relationship relationship1 = new Relationship();
        relationship1.setId(1);
        relationship1.setDocumentId(1);
        relationship1.setElementId(2);

        Relationship relationship2 = new Relationship();
        relationship2.setId(2);
        relationship2.setDocumentId(3);
        relationship2.setElementId(2);

        return List.of(relationship1, relationship2);
    }

    public static List<Relationship> getRelationshipsForDocument1(){
        Relationship relationship = new Relationship();
        relationship.setId(1);
        relationship.setElementId(2);
        relationship.setDocumentId(1);

        return List.of(relationship);
    }

    public static Relationship getRelationshipNotInDatabase(){
        Relationship relationship = new Relationship();
        relationship.setId(3);
        relationship.setElementId(3);
        relationship.setDocumentId(5);
        relationship.setName("link between user 2's note and todo");
        relationship.setDescription("small description");

        return relationship;
    }

}
