package com.dev10.models.DTO;

import com.dev10.models.Document;
import com.dev10.models.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class DocumentRequest {
    @Valid
    @NotNull(message = "User cannot be missing")
    private User user;

    @Valid
    @NotNull(message = "Document cannot be missing")
    private Document document;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
