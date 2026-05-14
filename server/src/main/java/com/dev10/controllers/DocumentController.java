package com.dev10.controllers;

import com.dev10.domain.DocumentService;
import com.dev10.models.DTO.NewDocumentRequest;
import com.dev10.models.DTO.ResourceRequest;
import com.dev10.models.DTO.Result;
import com.dev10.models.DataAccessException;
import com.dev10.models.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
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


    @PostMapping("/create")
    public ResponseEntity<Object> createDocument(@RequestHeader("Authorization") String auth,
                                                 @RequestBody NewDocumentRequest request) throws DataAccessException {
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
}
