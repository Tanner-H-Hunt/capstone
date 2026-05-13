package com.dev10;

import com.dev10.models.Directory;
import com.dev10.models.Document;
import com.dev10.models.DocumentType;
import com.dev10.models.User;

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
}
