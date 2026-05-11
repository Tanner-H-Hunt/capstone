package com.dev10.models;

import java.util.ArrayList;
import java.util.List;

public class Result <T>{
    private final List<String> errorMessages = new ArrayList<>();
    private T payload;

    public T getPayload(){
        return payload;
    }

    public void setPayload(T payload){
        this.payload = payload;
    }

    public List<String> getErrorMessages(){
        return errorMessages;
    }

    public void addErrorMessage(String message){
        errorMessages.add(message);
    }

    public boolean isSuccess(){
        return errorMessages.isEmpty();
    }
}
