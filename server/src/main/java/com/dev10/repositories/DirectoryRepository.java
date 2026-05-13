package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.Directory;
import com.dev10.models.User;

import java.util.List;

public interface DirectoryRepository {
    public List<Directory> getRootDirectories(User user);
    public List<Directory> getDirectoriesInDirectory(int id);
    Directory getDirectoryById(int id) throws DataAccessException;
    public boolean deleteDirectory(int id);
    public boolean editDirectory(Directory directory);
    public Directory createDirectory(Directory directory);
}
