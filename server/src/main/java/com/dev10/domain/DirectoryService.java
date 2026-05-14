package com.dev10.domain;

import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.Directory;
import com.dev10.models.User;
import com.dev10.repositories.DirectoryRepository;
import com.dev10.repositories.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DirectoryService {

    private final DirectoryRepository directoryRepository;
    private final UserRepository userRepository;
    private final Validator validator;

    public DirectoryService(DirectoryRepository directoryRepository,
                            Validator validator,
                            UserRepository userRepository){
        this.directoryRepository = directoryRepository;
        this.validator = validator;
        this.userRepository = userRepository;
    }

    public List<Directory> getRootDirectories(User user){
        return directoryRepository.getRootDirectories(user);
    }

    public List<Directory> getDirectoriesInDirectory(int id){
        return directoryRepository.getDirectoriesInDirectory(id);
    }

    public Directory getDirectoryById(int id) throws DataAccessException {
        return directoryRepository.getDirectoryById(id);
    }

    public Result<Directory> createDirectory(Directory directory) throws DataAccessException {
        Result<Directory> result = new Result<>();
        if(directory == null){
            result.addErrorMessage("directory cannot be null");
            return result;
        }

        // don't modify the directories ID
        if(directory.getId() != 0){
            result.addErrorMessage("May not preemptively set a directory ID");
        }

        // null, blank, and range value checks
        Set<ConstraintViolation<Directory>> violations = validator.validate(directory);
        for(ConstraintViolation<Directory> violation : violations){
            result.addErrorMessage(violation.getMessage());
        }

        // make sure there is only one root directory
        if(directory.getParentDirectoryId() == 0){
            User user = userRepository.findById(directory.getAccountId());
            List<Directory> rootDirectories = directoryRepository.getRootDirectories(user);
            if(rootDirectories.size() > 1){
                result.addErrorMessage("Cannot create multiple root directories for one user");
            }
        }
        // make sure the parent directory actually exists
        else{
            Directory proposedParentDirectory = directoryRepository.getDirectoryById(directory.getParentDirectoryId());
            if(proposedParentDirectory == null){
                result.addErrorMessage("Must belong to a parent directory");
            }
        }
        
        if(result.isSuccess()){
            Directory createdDirectory = directoryRepository.createDirectory(directory);
            result.setPayload(createdDirectory);
        }

        return result;
    }
}
