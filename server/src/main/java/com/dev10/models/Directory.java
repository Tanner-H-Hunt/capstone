package com.dev10.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class Directory {
    private int id;

    @Min(value = 0, message = "parent directory ID must be valid")
    private int parentDirectoryId;

    @Min(value = 0, message = "account id must be a valid id")
    private int accountId;

    @NotNull(message = "Directory name cannot be null")
    @NotBlank(message = "directory name may not be blank")
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Directory directory = (Directory) o;
        return getId() == directory.getId()
                && getParentDirectoryId() == directory.getParentDirectoryId()
                && getAccountId() == directory.getAccountId()
                && Objects.equals(getDirectoryName(), directory.getDirectoryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getParentDirectoryId(), getAccountId(), getDirectoryName());
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\": " + id +
                ", \"parentDirectoryId\":" + parentDirectoryId +
                ", \"accountId\"" + accountId +
                ", \"directoryName\": \"" + directoryName + "\"" +
                '}';
    }
}
