package com.dev10.controllers;

import com.dev10.models.DTO.NewDocumentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/document")
public class DocumentsController {

    @PutMapping("/create")
    public ResponseEntity<Object> createDocument(@RequestHeader("Authentication") String auth,
                                                 @RequestBody NewDocumentRequest request){


        return null;
    }
}
