package com.dev10.models.DTO;

import com.dev10.domain.DirectoryService;
import com.dev10.domain.DocumentElementService;
import com.dev10.domain.DocumentService;
import com.dev10.domain.UserService;
import com.dev10.models.DataAccessException;
import com.dev10.models.Directory;
import com.dev10.models.Document;
import com.dev10.models.User;
import com.dev10.models.docelements.Attribute;
import com.dev10.models.docelements.DocumentElement;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Represents a single transaction with values validated on the server side to compare
 * with user-provided values
 */
@Component
public class ResourceRequest {
    private User user;
    private Document document;
    private Directory directory;
    private DocumentElement element;
    private Attribute attribute;

    private final DirectoryService directoryService;
    private final DocumentService documentService;
    private final UserService userService;
    private final DocumentElementService documentElementService;

    public ResourceRequest(DirectoryService directoryService,
                           DocumentService documentService,
                           UserService userService,
                           DocumentElementService documentElementService){
        this.directoryService = directoryService;
        this.documentService = documentService;
        this.userService = userService;
        this.documentElementService = documentElementService;
    }

    public User getUser() {
        return user;
    }

    public Document getDocument() {
        return document;
    }

    public Directory getDirectory() {
        return directory;
    }

    public Attribute getAttribute(){
        return this.attribute;
    }

    public DocumentElement getElement(){
        return this.element;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public void validateDocument(int documentId) throws DataAccessException {
        this.document = documentService.getDocumentById(documentId);
        if(document != null){
            validateParentDirectory(document.getParentDirectoryId());
        }
    }

    public void validateParentDirectory(int directoryId) throws DataAccessException {
        this.directory = directoryService.getDirectoryById(directoryId);
        if(directory == null){
            return;
        }
        setUser( userService.findById(directory.getAccountId()) );
    }

    public void validateElement(int elementId) throws DataAccessException{
        this.element = documentElementService.getElementById(elementId);
        if(this.element != null){
            validateDocument(element.getDocumentId());
        }
    }

    public void validateAttribute(int attributeId) throws DataAccessException {
        this.attribute = documentElementService.getAttributeById(attributeId);
        if(attribute != null){
            validateElement(attribute.getDocumentElementId());
        }
    }

    public void clear(){
        this.user = null;
        this.document = null;
        this.directory = null;
        this.attribute = null;
        this.element = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ResourceRequest that = (ResourceRequest) o;

        return Objects.equals(getUser().getEmail(), that.getUser().getEmail())
                && Objects.equals(getDocument(), that.getDocument())
                && Objects.equals(getDirectory(), that.getDirectory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getDocument(), getDirectory());
    }
}
