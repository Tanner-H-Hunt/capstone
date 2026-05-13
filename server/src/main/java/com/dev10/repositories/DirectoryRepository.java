package com.dev10.repositories;

import com.dev10.models.Directory;
import com.dev10.models.User;

import java.util.List;

public interface DirectoryRepository {
    public List<Directory> getRootDirectories(User user);
    public List<Directory> getDirectoriesInDirectory(int id);
    public boolean deleteDirectory(int id);
    public boolean editDirectory(Directory directory);
    public Directory createDirectory(Directory directory);
}
