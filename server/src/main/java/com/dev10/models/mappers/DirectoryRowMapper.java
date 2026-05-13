package com.dev10.models.mappers;

import com.dev10.models.Directory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DirectoryRowMapper implements RowMapper<Directory> {
    @Override
    public Directory mapRow(ResultSet rs, int rowNum) throws SQLException {
        Directory directory = new Directory();

        directory.setId(rs.getInt("directory_id"));
        directory.setAccountId(rs.getInt("account_id"));
        directory.setParentDirectoryId(rs.getInt("parent_directory"));
        directory.setDirectoryName(rs.getString("directory_name"));
        
        return directory;
    }
}
