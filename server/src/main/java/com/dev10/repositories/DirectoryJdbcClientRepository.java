package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.Directory;
import com.dev10.models.User;
import com.dev10.models.mappers.DirectoryRowMapper;
import com.dev10.models.mappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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
    public Directory getDirectoryById(int id) throws DataAccessException {
        final String sql = """
                SELECT * from account a
                INNER JOIN directory d on d.account_id = a.account_id
                WHERE d.directory_id = :id;
                """;

        try{
            return client.sql(sql)
                    .param("id", id)
                    .query(new DirectoryRowMapper())
                    .optional()
                    .orElse(null);
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while trying to find the user attached to a directory", e);
        }
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
    public Directory createDirectory(Directory directory) throws DataAccessException {
        final String sql = """
                INSERT into directory (account_id, parent_directory, directory_name) values
                (:account_id, :parent_directory, :directory_name)
                """;
        try{
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            client.sql(sql)
                .param("account_id", directory.getAccountId())
                .param("parent_directory", directory.getParentDirectoryId())
                .param("directory_name", directory.getDirectoryName())
                .update(keyHolder);

            directory.setId(keyHolder.getKey().intValue());
            return directory;
        } catch (Exception e){
            throw new DataAccessException("Something went wrong while creating a new directory", e);
        }
    }

}
