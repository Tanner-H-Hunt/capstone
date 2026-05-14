package com.dev10.domain;

import com.dev10.models.DataAccessException;
import com.dev10.models.DTO.Result;
import com.dev10.models.User;
import com.dev10.repositories.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Validator validator;

    public UserService(UserRepository userRepository, Validator validator){
        this.userRepository = userRepository;
        this.validator = validator;
    }

    public User findByEmail(User user) throws DataAccessException {
        return userRepository.getUserWithEmail(user.getEmail());
    }

    public User findById(int id) throws DataAccessException {
        return userRepository.findById(id);
    }

    public Result<User> createAccount(User user) throws DataAccessException {
        Result<User> result = new Result<>();

        if(user == null){
            result.addErrorMessage("Cannot create a null user");
            return result;
        }

        if(user.getId() != 0){
            result.addErrorMessage("Cannot preemptively set the users ID. Expected ID: 0");
        }

        if(findByEmail(user) != null){
            result.addErrorMessage("A user with that email already exists");
        }

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if(!violations.isEmpty()){
            for(ConstraintViolation<User> violation : violations){
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }

        if(result.isSuccess()){
            User updatedUser = userRepository.createUser(user);
            result.setPayload(updatedUser);
        }

        return result;
    }
}
