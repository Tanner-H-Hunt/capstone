package com.dev10.models;

public class Directory {
    private int id;
    private int parentDirectoryId;
    private int accountId;
    private String directoryName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentDirectoryId() {
        return parentDirectoryId;
    }

    public void setParentDirectoryId(int parentDirectoryId) {
        this.parentDirectoryId = parentDirectoryId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }
}
