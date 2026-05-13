package com.dev10.repositories;

import com.dev10.models.Directory;
import com.dev10.models.User;

import java.util.List;

public interface DirectoryRepository {
    public List<Directory> getRootDirectories(User user);
    public List<Directory> getDirectoriesInDirectory(User user);
}
