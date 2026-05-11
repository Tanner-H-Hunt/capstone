package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.User;
import com.dev10.models.mappers.UserRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcClientRepository implements UserRepository {

    private final JdbcClient client;

    public UserJdbcClientRepository(JdbcClient client){
        this.client = client;
    }

    @Override
    public User getUserWithEmail(String email) throws DataAccessException {
        final String sql = """
                SELECT * FROM account WHERE email=:email
                """;

        try{
            return client.sql(sql)
                    .param("email", email)
                    .query(new UserRowMapper())
                    .optional().orElse(null);

        } catch (Exception e) {
            throw new DataAccessException("Something went wrong while fetching user data from the database", e);
        }

    }

    @Override
    public User createUser(User user) throws DataAccessException {
        final String sql = """
                INSERT INTO account (email, password) values
                (:email, :password)
                """;
        try{
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            client.sql(sql)
                    .param("email", user.getEmail())
                    .param("password", user.getPassword())
                    .update(keyHolder);

            user.setId(keyHolder.getKey().intValue());
            return user;

        } catch (Exception e) {
            throw new DataAccessException("Something went wrong while generating a user in the database", e);
        }
    }

    @Override
    public User findById(int id) throws DataAccessException {
        final String sql = """
                SELECT * FROM account WHERE account_id = :id;
                """;
        return client.sql(sql)
                .param("id", id)
                .query(new UserRowMapper())
                .optional().orElse(null);
    }
}
