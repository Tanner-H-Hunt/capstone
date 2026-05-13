package com.dev10.repositories;

import com.dev10.models.Directory;
import com.dev10.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DirectoryJdbcClientRepository implements DirectoryRepository{
    @Override
    public List<Directory> getRootDirectories(User user) {
        return List.of();
    }

    @Override
    public List<Directory> getDirectoriesInDirectory(int id) {
        return List.of();
    }

    @Override
    public boolean deleteDirectory(int id) {
        return false;
    }

    @Override
    public boolean editDirectory(Directory directory) {
        return false;
    }

    @Override
    public Directory createDirectory(Directory directory) {
        return null;
    }

}
