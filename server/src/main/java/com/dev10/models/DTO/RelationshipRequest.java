package com.dev10.models.DTO;

import com.dev10.models.Relationship;
import com.dev10.models.User;

public class RelationshipRequest {
    private User user;
    private Relationship relationship;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }
}
