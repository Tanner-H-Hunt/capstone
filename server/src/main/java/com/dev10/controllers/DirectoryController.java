package com.dev10.controllers;

import com.dev10.domain.DirectoryService;
import com.dev10.domain.DocumentService;
import com.dev10.domain.UserService;
import com.dev10.models.*;
import com.dev10.models.DTO.NewDirectoryRequest;
import com.dev10.models.DTO.ResourceRequest;
import com.dev10.models.DTO.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/directory")
public class DirectoryController {

    private final Authenticator authenticator;
    private final DocumentService documentService;
    private final DirectoryService directoryService;
    private final UserService userService;
    private final ResourceRequest resourceRequest;

    public DirectoryController(Authenticator authenticator,
                               DocumentService documentService,
                               DirectoryService directoryService,
                               UserService userService,
                               ResourceRequest resourceRequest){
        this.authenticator = authenticator;
        this.documentService = documentService;
        this.directoryService = directoryService;
        this.userService = userService;
        this.resourceRequest = resourceRequest;
    }

    @PostMapping
    public ResponseEntity<Object> getHomepageContents(@RequestHeader("Authorization") String authHeader,
                                                      @RequestBody User user) throws DataAccessException {
        if(userService.findByEmail(user) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if(!authenticator.isValidBearerToken(user, authHeader)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        }

        List<Document> documents = documentService.getDocumentsInRoot(user);
        List<Directory> directories = directoryService.getRootDirectories(user);

        String response = directoryAndDocumentListsToJson(documents, directories);

        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/:directoryId")
    public ResponseEntity<Object> getDirectoryContents(@PathVariable int directoryId,
                                                       @RequestBody User user,
                                                       @RequestHeader("Authorization") String authHeader) throws DataAccessException{

        // person is who they say they are
        if(!authenticator.isValidBearerToken(user, authHeader)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // person is authorized to do what they are asking
        resourceRequest.validateParentDirectory(directoryId);
        if(!authenticator.isUserPermitted(user, resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Document> documents = documentService.getDocumentsInDirectory(directoryId);
        List<Directory> directories = directoryService.getDirectoriesInDirectory(directoryId);

        String response = directoryAndDocumentListsToJson(documents, directories);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createDirectory(@RequestHeader("Authorization") String authHeader,
                                                  @RequestBody NewDirectoryRequest request) throws DataAccessException {
        if(request == null){
            return ResponseEntity.badRequest().build();
        }

        if(request.getDirectory() == null || request.getUser() == null){
            return ResponseEntity.badRequest().build();
        }

        // user is who they say they are
        if(!authenticator.isValidBearerToken(request.getUser(), authHeader)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // user is allowed to modify that resource
        resourceRequest.validateParentDirectory(request.getDirectory().getParentDirectoryId());
        if(!authenticator.isUserPermitted(request.getUser(), resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Result<Directory> result = directoryService.createDirectory(request.getDirectory());
        if(result.isSuccess()){
            return ResponseEntity.status(HttpStatus.CREATED).body(result.getPayload().toString());
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getErrorMessages());
        }
    }

    private String directoryAndDocumentListsToJson(List<Document> documents, List<Directory> directories){
        StringBuilder response = new StringBuilder();
        response.append("{ \"documents\": [ ");
        for(int i = 0; i < documents.size(); i++){
            response.append(documents.get(i).toString());
            if(i != documents.size() - 1){
                response.append(",");
            }
        }
        response.append("], \"directories\": [");
        for(int i = 0; i < directories.size(); i++){
            response.append(directories.get(i).toString());
            if(i != directories.size() - 1){
                response.append(",");
            }
        }
        response.append("]}");

        return response.toString();
    }

}
