package com.dev10.controllers;

import com.dev10.domain.ElementService;
import com.dev10.models.DTO.ElementRequest;
import com.dev10.models.DTO.ResourceRequest;
import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.User;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.Element;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/element")
public class ElementController {

    private final ElementService service;
    private final Authenticator authenticator;
    private final ResourceRequest resourceRequest;

    public ElementController(ElementService service,
                             Authenticator authenticator,
                             ResourceRequest resourceRequest){
        this.service = service;
        this.authenticator = authenticator;
        this.resourceRequest = resourceRequest;
    }

    @PostMapping
    public ResponseEntity<Object> createElement(@RequestHeader("Authorization") String auth,
                                                @RequestBody ElementRequest request) throws DataAccessException {
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

        Result<Element> result = service.create(request.getElement());

        if(result.isSuccess()){
            return ResponseEntity.status(HttpStatus.CREATED).body(result.getPayload().toString());
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getErrorMessages());
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateElement(@RequestHeader("Authorization") String auth,
                                                @RequestBody ElementRequest request) throws DataAccessException {
        if(request == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("request body required");
        }

        // user is who they say they are
        if(!authenticator.isValidBearerToken(request.getUser(), auth)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }

        // user is allowed to modify this element
        resourceRequest.validateElement(request.getElement().getElementId());
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

        Result<Element> result = service.updateElement(request.getElement());

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

    @PostMapping("/{id}")
    public ResponseEntity<Object> getElementsForDocument(@RequestHeader("Authorization") String auth,
                                                         @RequestBody User user,
                                                         @PathVariable("id") int id) throws DataAccessException {
        if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User credentials required");
        }

        if(!authenticator.isValidBearerToken(user, auth)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Must login to view this document");
        }

        resourceRequest.validateDocument(id);
        if(!authenticator.isUserPermitted(user, resourceRequest)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to view this document");
        }

        StringBuilder body = new StringBuilder();
        List<Element> elements = service.getElementsForDocument(id);
        body.append("{ \"elements\": [");
        for(int i = 0; i < elements.size(); i++){
            body.append(elements.get(i).toString());
            if(i != elements.size() - 1){
                body.append(",");
            }
        }
        body.append("]}");

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
