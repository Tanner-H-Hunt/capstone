package com.dev10.models.DTO;

import com.dev10.models.Directory;
import com.dev10.models.User;

public class NewDirectoryRequest {
    private User user;
    private Directory directory;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }
}
