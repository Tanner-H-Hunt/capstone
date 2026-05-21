package com.dev10.controllers;

import com.dev10.domain.RelationshipService;
import com.dev10.models.DTO.RelationshipRequest;
import com.dev10.models.DTO.ResourceRequest;
import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.Document;
import com.dev10.models.Relationship;
import com.dev10.models.User;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/relationship")
public class RelationshipController {
    private final RelationshipService relationshipService;
    private final ResourceRequest resourceRequest;
    private final Authenticator authenticator;

    public RelationshipController(RelationshipService relationshipService,
                                  ResourceRequest resourceRequest,
                                  Authenticator authenticator){
        this.relationshipService = relationshipService;
        this.resourceRequest = resourceRequest;
        this.authenticator = authenticator;
    }

    @PostMapping("/document/{id}")
    public ResponseEntity<Object> getRelationshipsForDocument(
            @RequestHeader("Authorization") String auth,
            @RequestBody User user,
            @PathVariable("id") int id) throws DataAccessException {

        if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User credentials required");
        }

        if(!authenticator.isValidBearerToken(user, auth)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Provided credentials do not match hash value");
        }

        resourceRequest.validateDocument(id);
        if(!authenticator.isUserPermitted(user,resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This document does not belong to you");
        }

        List<Relationship> relationships = relationshipService.getRelationshipsForDocument(id);

        return ResponseEntity.status(HttpStatus.OK).body(relationships);
    }

    @PostMapping("/element/{id}")
    public ResponseEntity<Object> getRelationshipsForElement(
            @RequestHeader("Authorization") String auth,
            @RequestBody User user,
            @PathVariable("id") int id) throws DataAccessException {

        if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User credentials required");
        }

        if(!authenticator.isValidBearerToken(user, auth)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Provided credentials do not match hash value");
        }

        resourceRequest.validateElement(id);
        if(!authenticator.isUserPermitted(user,resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This document does not belong to you");
        }

        List<Relationship> relationships = relationshipService.getRelationshipsForElement(id);

        return ResponseEntity.status(HttpStatus.OK).body(relationships);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createRelationship(
            @RequestHeader("Authorization") String auth,
            @RequestBody RelationshipRequest request) throws DataAccessException {

        if(request.getUser() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User credentials required");
        }

        if(!authenticator.isValidBearerToken(request.getUser(), auth)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Provided credentials do not match the hash value");
        }

        resourceRequest.validateElement(request.getRelationship().getElementId());
        if(!authenticator.isUserPermitted(request.getUser(),resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This element does not belong to you");
        }

        resourceRequest.validateDocument(request.getRelationship().getDocumentId());
        if(!authenticator.isUserPermitted(request.getUser(),resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This document does not belong to you");
        }

        Result<Relationship> result = relationshipService.create(request.getRelationship());
        if(result.isSuccess()){
            return ResponseEntity.status(HttpStatus.CREATED).body(result.getPayload());
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getErrorMessages());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRelationship(
            @RequestHeader("Authorization") String auth,
            @RequestBody User user,
            @PathVariable("id") int id) throws DataAccessException {

        if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User credentials required");
        }

        if(!authenticator.isValidBearerToken(user, auth)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Provided credentials do not match hash value");
        }

        resourceRequest.validateRelationship(id);
        if(!authenticator.isUserPermitted(user,resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This relationship does not belong to you");
        }

        boolean deleted = relationshipService.delete(id);

        if(deleted){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/element/doc/{id}")
    public ResponseEntity<Object> fetchDocIdForElement(@RequestHeader("Authorization")String auth,
                                                       @RequestBody User user,
                                                       @PathVariable("id")int id) throws DataAccessException {
        if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User credentials required");
        }

        if(!authenticator.isValidBearerToken(user, auth)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Provided credentials do not match hash value");
        }

        resourceRequest.validateRelationship(id);
        if(!authenticator.isUserPermitted(user,resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This relationship does not belong to you");
        }

        Document document = relationshipService.getOriginatingDocumentForRelationshipElement(id);

        if(document == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find a relationship with that ID");
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(document);
        }
    }
}
