package com.dev10;

import com.dev10.models.FileSystemObject;
import com.dev10.models.FileSystemObjectType;
import com.dev10.models.User;

import java.util.List;

public class TestDataHelper {
    public static List<User> getAllUsers(){
        User user1 = new User();
        User user2 = new User();

        user1.setId(1);
        user1.setEmail("a@a.com");
        user1.setPassword("a");

        user2.setId(2);
        user2.setEmail("b@b.com");
        user2.setPassword("b");

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

        return user;
    }

    public static List<FileSystemObject> getFileSystemObjectsForUser1(){
        FileSystemObject fso1 = new FileSystemObject();
        fso1.setId(1);
        fso1.setName("user1-directory");
        fso1.setDocumentType(FileSystemObjectType.DIRECTORY);
        fso1.setParentDirectoryId(0);

        FileSystemObject fso2 = new FileSystemObject();
        fso2.setId(1);
        fso2.setName("user1-todo");
        fso2.setDocumentType(FileSystemObjectType.TODO);
        fso2.setParentDirectoryId(0);

        FileSystemObject fso3 = new FileSystemObject();
        fso3.setId(2);
        fso3.setName("user1-uml");
        fso3.setDocumentType(FileSystemObjectType.UML);
        fso3.setParentDirectoryId(1);

        FileSystemObject fso4 = new FileSystemObject();
        fso4.setId(3);
        fso4.setName("user1-note");
        fso4.setDocumentType(FileSystemObjectType.NOTE);
        fso4.setParentDirectoryId(0);

        return List.of(
                fso1,
                fso2,
                fso3,
                fso4
        );
    }

    public static List<FileSystemObject> getFileSystemObjectsForUser2(){
        FileSystemObject fso1 = new FileSystemObject();
        fso1.setId(2);
        fso1.setName("user2-directory");
        fso1.setDocumentType(FileSystemObjectType.DIRECTORY);
        fso1.setParentDirectoryId(0);

        FileSystemObject fso2 = new FileSystemObject();
        fso2.setId(2);
        fso2.setName("user2-todo");
        fso2.setDocumentType(FileSystemObjectType.TODO);
        fso2.setParentDirectoryId(0);

        FileSystemObject fso3 = new FileSystemObject();
        fso3.setId(3);
        fso3.setName("user2-uml");
        fso3.setDocumentType(FileSystemObjectType.UML);
        fso3.setParentDirectoryId(1);

        FileSystemObject fso4 = new FileSystemObject();
        fso4.setId(4);
        fso4.setName("user2-note");
        fso4.setDocumentType(FileSystemObjectType.NOTE);
        fso4.setParentDirectoryId(0);

        return List.of(
                fso1,
                fso2,
                fso3,
                fso4
        );
    }
}
