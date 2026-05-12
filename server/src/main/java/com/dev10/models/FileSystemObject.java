package com.dev10.models;

public class FileSystemObject {
    private int id;
    private FileSystemObjectType fileSystemObjectType;
    private String name;
    private int parentDirectoryId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FileSystemObjectType getDocumentType() {
        return fileSystemObjectType;
    }

    public void setDocumentType(FileSystemObjectType fileSystemObjectType) {
        this.fileSystemObjectType = fileSystemObjectType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentDirectoryId() {
        return parentDirectoryId;
    }

    public void setParentDirectoryId(int parentDirectoryId) {
        this.parentDirectoryId = parentDirectoryId;
    }
}
