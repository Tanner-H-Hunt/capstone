package com.dev10.repositories;

import com.dev10.models.FileSystemObject;
import com.dev10.models.User;

import java.util.List;

public interface DocumentRepository {
    List<FileSystemObject> getFSOsInDirectory(User user, int directoryId);
    List<FileSystemObject> getHomeFSOs(User user);
    List<FileSystemObject> getAllFSO(User user);
}
