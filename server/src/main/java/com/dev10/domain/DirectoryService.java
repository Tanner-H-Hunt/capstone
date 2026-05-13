package com.dev10.domain;

import com.dev10.models.Directory;
import com.dev10.models.User;
import com.dev10.repositories.DirectoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectoryService {

    private final DirectoryRepository directoryRepository;

    public DirectoryService(DirectoryRepository directoryRepository){
        this.directoryRepository = directoryRepository;
    }

    public List<Directory> getRootDirectories(User user){
        return directoryRepository.getRootDirectories(user);
    }

    public List<Directory> getDirectoriesInDirectory(int id){
        return directoryRepository.getDirectoriesInDirectory(id);
    }
}
