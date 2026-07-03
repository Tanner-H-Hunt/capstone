package com.dev10.controllers;

import com.dev10.domain.DocumentService;
import com.dev10.models.DTO.DocumentRequest;
import com.dev10.models.DTO.ResourceRequest;
import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.Document;
import com.dev10.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    private final Authenticator authenticator;
    private final DocumentService service;
    private final ResourceRequest resourceRequest;

    public DocumentController(Authenticator authenticator, DocumentService service, ResourceRequest resourceRequest){
        this.authenticator = authenticator;
        this.service = service;
        this.resourceRequest = resourceRequest;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> getById(@RequestHeader("Authorization") String auth,
                                          @RequestBody User user,
                                          @PathVariable("id") int id) throws DataAccessException {
        if(user == null || auth == null){
            return ResponseEntity.badRequest().body("Auth header and user are required");
        }

        if(!authenticator.isValidBearerToken(user, auth)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        resourceRequest.validateDocument(id);
        if(!authenticator.isUserPermitted(user, resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Document document = service.getDocumentById(id);
        if(document == null){
            return ResponseEntity.notFound().build();
        } else{
            return ResponseEntity.ok(document);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Object> createDocument(@RequestHeader("Authorization") String auth,
                                                 @RequestBody DocumentRequest request) throws DataAccessException {
        if(request == null){
            return ResponseEntity.badRequest().build();
        }

        if(!authenticator.isValidBearerToken(request.getUser(), auth)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        resourceRequest.validateParentDirectory(request.getDocument().getParentDirectoryId());
        if(!authenticator.isUserPermitted(request.getUser(), resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Result<Document> result = service.createDocument(request.getDocument());
        if(result.isSuccess()){
            return ResponseEntity.status(HttpStatus.CREATED).body(result.getPayload());
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getErrorMessages());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editDocument(@RequestHeader("Authorization") String auth,
                                               @RequestBody DocumentRequest request,
                                               @PathVariable("id") int id) throws DataAccessException{
        if(request == null){
            return ResponseEntity.badRequest().build();
        }

        if(id != request.getDocument().getId()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // user is who they say they are
        if(!authenticator.isValidBearerToken(request.getUser(), auth)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // user is allowed to child this document to the requested directory
        resourceRequest.validateParentDirectory(request.getDocument().getParentDirectoryId());
        if(!authenticator.isUserPermitted(request.getUser(), resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // user is allowed to manipulate this document
        resourceRequest.validateDocument(request.getDocument().getId());
        if(!authenticator.isUserPermitted(request.getUser(), resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // validate through the service
        Result<Document> result = service.editDocument(request.getDocument());
        if(result.isSuccess()){
            return ResponseEntity.status(HttpStatus.CREATED).body(result.getPayload());
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getErrorMessages());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDocument(@RequestHeader("Authorization") String auth,
                                                 @RequestBody User user,
                                                 @PathVariable("id") int id) throws DataAccessException{
        // user is who they say they are
        if(!authenticator.isValidBearerToken(user, auth)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // user is allowed to delete the requested document
        resourceRequest.validateDocument(id);
        if(!authenticator.isUserPermitted(user, resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        boolean result = service.delete(id);

        if(result){
            return ResponseEntity.noContent().build();
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> getAllDocuments(@RequestHeader("Authorization") String auth,
                                          @RequestBody User user) throws DataAccessException {
        if(user == null || auth == null){
            return ResponseEntity.badRequest().body("Auth header and user are required");
        }

        if(!authenticator.isValidBearerToken(user, auth)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Document> document = service.getAllDocuments(user);
        if(document == null){
            return ResponseEntity.notFound().build();
        } else{
            return ResponseEntity.ok(document);
        }
    }
}
