package com.dev10.domain;

import com.dev10.models.DataAccessException;
import com.dev10.models.DTO.Result;
import com.dev10.models.Directory;
import com.dev10.models.User;
import com.dev10.models.UserPrincipal;
import com.dev10.repositories.DirectoryRepository;
import com.dev10.repositories.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final Validator validator;
    private final DirectoryRepository directoryRepository;

    public UserService(UserRepository userRepository, Validator validator, DirectoryRepository directoryRepository){
        this.userRepository = userRepository;
        this.validator = validator;
        this.directoryRepository = directoryRepository;
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

            // auto generate the users root directory
            Directory usersRootDirectory = new Directory();
            usersRootDirectory.setParentDirectoryId(0);
            usersRootDirectory.setId(0);
            usersRootDirectory.setDirectoryName("root");
            usersRootDirectory.setAccountId(updatedUser.getId());
            directoryRepository.createDirectory(usersRootDirectory);

            result.setPayload(updatedUser);
        }

        return result;
    }

    /**
     * Spring Securities authentication method
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            User user = userRepository.getUserWithEmail(username);
            if(user == null){
                throw new UsernameNotFoundException("Invalid username / password");
            }
            return new UserPrincipal(user);

        } catch (DataAccessException e){
            e.printStackTrace();
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
