package com.dev10.repositories;

import com.dev10.models.Directory;
import com.dev10.models.User;
import com.dev10.models.mappers.DirectoryRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DirectoryJdbcClientRepository implements DirectoryRepository{
    @Autowired
    JdbcClient client;

    @Override
    public List<Directory> getRootDirectories(User user) {
        final String sql = """
                SELECT * from directory
                WHERE account_id = :account_id
                AND parent_directory IS NULL;
                """;

        return client.sql(sql)
                .param("account_id", user.getId())
                .query(new DirectoryRowMapper())
                .list();
    }

    @Override
    public List<Directory> getDirectoriesInDirectory(int id) {
        final String sql = """
                SELECT * from directory
                WHERE parent_directory = :directory_id
                """;

        return client.sql(sql)
                .param("directory_id", id)
                .query(new DirectoryRowMapper())
                .list();
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
