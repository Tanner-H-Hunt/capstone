package com.dev10.models.DTO;

import com.dev10.domain.DirectoryService;
import com.dev10.domain.DocumentService;
import com.dev10.domain.UserService;
import com.dev10.models.DataAccessException;
import com.dev10.models.Directory;
import com.dev10.models.Document;
import com.dev10.models.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final DirectoryService directoryService;
    private final DocumentService documentService;
    private final UserService userService;

    public ResourceRequest(DirectoryService directoryService,
                           DocumentService documentService,
                           UserService userService){
        this.directoryService = directoryService;
        this.documentService = documentService;
        this.userService = userService;
    }

    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(int documentId) throws DataAccessException {
        this.document = documentService.getDocumentById(documentId);
        setDirectory(document.getParentDirectoryId());
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(int directoryId) throws DataAccessException {
        this.directory = directoryService.getDirectoryById(directoryId);
        if(directory == null){
            return;
        }
        setUser( userService.findById(directory.getAccountId()) );
    }

    public void clear(){
        this.user = null;
        this.document = null;
        this.directory = null;
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
