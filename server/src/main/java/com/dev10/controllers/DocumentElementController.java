package com.dev10.controllers;

import com.dev10.domain.DocumentElementService;
import com.dev10.models.DTO.DocumentElementRequest;
import com.dev10.models.DTO.ResourceRequest;
import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.User;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/element")
public class DocumentElementController {

    private final DocumentElementService service;
    private final Authenticator authenticator;
    private final ResourceRequest resourceRequest;

    public DocumentElementController(DocumentElementService service,
                                     Authenticator authenticator,
                                     ResourceRequest resourceRequest){
        this.service = service;
        this.authenticator = authenticator;
        this.resourceRequest = resourceRequest;
    }

    @PostMapping
    public ResponseEntity<Object> createElement(@RequestHeader("Authorization") String auth,
                                                @RequestBody DocumentElementRequest request) throws DataAccessException {
        if(request == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("request body required");
        }

        // user is who they say they are
        if(!authenticator.isValidBearerToken(request.getUser(), auth)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }

        // user is allowed to push an element to the document
        resourceRequest.validateDocument(request.getElement().getDocumentId());
        if(!authenticator.isUserPermitted(request.getUser(), resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to use this resource");
        }

        Result<DocumentElement> result = service.create(request.getElement());

        if(result.isSuccess()){
            return ResponseEntity.status(HttpStatus.CREATED).body(result.getPayload());
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getErrorMessages());
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateElement(@RequestHeader("Authorization") String auth,
                                                @RequestBody DocumentElementRequest request) throws DataAccessException {
        if(request == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("request body required");
        }

        // user is who they say they are
        if(!authenticator.isValidBearerToken(request.getUser(), auth)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }

        // user is allowed to modify this element
        resourceRequest.validateElement(request.getElement().getDocumentElementId());
        if(!authenticator.isUserPermitted(request.getUser(), resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to use this element");
        }

        // user is allowed to modify this attribute
        for(Attribute attribute : request.getElement().getAttributes()){
            resourceRequest.validateAttribute(attribute.getAttributeId());
            if(!authenticator.isUserPermitted(request.getUser(), resourceRequest)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to modify this attribute");
            }
        }

        Result<DocumentElement> result = service.updateElement(request.getElement());

        if(result.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(result.getPayload());
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getErrorMessages());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteElement(@RequestHeader("Authorization") String auth,
                                                @RequestBody User user,
                                                @PathVariable("id") int id) throws DataAccessException {
        if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This request requires a body");
        }

        if(!authenticator.isValidBearerToken(user, auth)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to delete this element");
        }

        resourceRequest.validateElement(id);
        if(!authenticator.isUserPermitted(user, resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete this element");
        }

        int rowsDeleted = service.delete(id);
        if(rowsDeleted > 0){
            return ResponseEntity.status(HttpStatus.OK).body(rowsDeleted + " rows deleted (element + attributes)");
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find element to delete");
        }
    }
}
