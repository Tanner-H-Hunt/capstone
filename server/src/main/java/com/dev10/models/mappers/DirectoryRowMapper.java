package com.dev10.models.mappers;

import com.dev10.models.Directory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DirectoryRowMapper implements RowMapper<Directory> {
    @Override
    public Directory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
