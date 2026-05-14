package com.dev10.controllers;

import com.dev10.domain.DirectoryService;
import com.dev10.domain.DocumentService;
import com.dev10.domain.UserService;
import com.dev10.models.*;
import com.dev10.models.DTO.ResourceRequest;
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

    @GetMapping
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


    @GetMapping("/:directoryId")
    public ResponseEntity<Object> getDirectoryContents(@PathVariable int directoryId,
                                                       @RequestBody User user,
                                                       @RequestHeader("Authorization") String authHeader) throws DataAccessException{

        // person is who they say they are
        if(!authenticator.isValidBearerToken(user, authHeader)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // person is authorized to do what they are asking
        resourceRequest.setDirectory(directoryId);
        if(!authenticator.isUserPermitted(user, resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Document> documents = documentService.getDocumentsInDirectory(directoryId);
        List<Directory> directories = directoryService.getDirectoriesInDirectory(directoryId);

        String response = directoryAndDocumentListsToJson(documents, directories);
        return ResponseEntity.ok().body(response);
    }

    private String directoryAndDocumentListsToJson(List<Document> documents, List<Directory> directories){
        StringBuilder response = new StringBuilder();
        response.append("{ \"documents\": [ ");
        for(Document document : documents){
            response.append(document.toString());
        }
        response.append("], \"directories\": [");
        for(Directory directory : directories){
            response.append(directory.toString());
        }
        response.append("]}");

        return response.toString();
    }

}
