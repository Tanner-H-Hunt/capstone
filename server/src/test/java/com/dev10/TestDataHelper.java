package com.dev10;

import com.dev10.models.Directory;
import com.dev10.models.Document;
import com.dev10.models.DocumentType;
import com.dev10.models.User;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import com.dev10.models.docelements.DocumentElementType;
import com.dev10.models.docelements.implementations.AttributeConfiguration;
import com.dev10.models.docelements.implementations.Box;
import com.dev10.models.docelements.implementations.Line;
import com.dev10.models.docelements.implementations.Text;
import com.dev10.repositories.DocumentElementRepository;
import org.springframework.beans.factory.annotation.Autowired;

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

    public static List<DocumentElement> getElementsForUser1Uml(){
        DocumentElement line = new Line();
        line.setDocumentElementId(1);
        line.setDocumentId(2);
        line.setDocumentElementType(DocumentElementType.LINE);

        DocumentElement box = new Box();
        box.setDocumentElementId(2);
        box.setDocumentId(2);
        box.setDocumentElementType(DocumentElementType.BOX);

        return List.of(line, box);
    }

    public static List<DocumentElement> getElementsForUser2Note(){
        DocumentElement text = new Text();
        text.setDocumentElementType(DocumentElementType.TEXT);
        text.setDocumentElementId(3);
        text.setDocumentId(4);

        return List.of(text);
    }

    public static DocumentElement getBoxNotInDatabase(){
        DocumentElement element = new Box();
        element.setDocumentId(2);
        element.setDocumentElementId(0);
        element.setDocumentElementType(DocumentElementType.BOX);
        return element;
    }

    public static List<Attribute> getAttributesForElement1(){
        return List.of();
    }
}
