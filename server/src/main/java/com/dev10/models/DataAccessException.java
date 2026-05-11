package com.dev10.models;

public class DataAccessException extends Exception{
    public DataAccessException(String message, Exception e){
        super(message, e);
    }
}
