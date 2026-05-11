package com.dev10.repositories;

import com.dev10.models.DataAccessException;
import com.dev10.models.User;

public interface UserRepository {
    User getUserWithEmail(String email) throws DataAccessException;
    User createUser(User user) throws DataAccessException;
    User findById(int id) throws DataAccessException;
}
