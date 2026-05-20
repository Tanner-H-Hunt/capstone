package com.dev10.models.DTO;

import com.dev10.models.User;
import com.dev10.models.docelements.DocumentElement;

public class DocumentElementRequest {
    private User user;
    private DocumentElement element;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DocumentElement getElement() {
        return element;
    }

    public void setElement(DocumentElement element) {
        this.element = element;
    }
}
