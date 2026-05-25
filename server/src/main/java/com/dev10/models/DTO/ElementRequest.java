package com.dev10.models.DTO;

import com.dev10.models.User;
import com.dev10.models.docelements.Element;

public class ElementRequest {
    private User user;
    private Element element;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
}
