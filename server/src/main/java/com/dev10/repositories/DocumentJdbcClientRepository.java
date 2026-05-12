package com.dev10.repositories;

import com.dev10.models.FileSystemObject;
import com.dev10.models.User;

import java.util.List;

public class DocumentJdbcClientRepository implements DocumentRepository{
    @Override
    public List<FileSystemObject> getFSOsInDirectory(User user, int directoryId) {
        return List.of();
    }

    @Override
    public List<FileSystemObject> getHomeFSOs(User user) {
        return List.of();
    }

    @Override
    public List<FileSystemObject> getAllFSO(User user) {
        return List.of();
    }
}
