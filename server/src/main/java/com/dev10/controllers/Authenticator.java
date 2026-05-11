package com.dev10.controllers;

import com.dev10.domain.UserService;
import com.dev10.models.DataAccessException;
import com.dev10.models.User;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class Authenticator {
    private final UserService userService;

    public Authenticator(UserService service){
        this.userService = service;
    }

    public boolean verify(String body, String token){
        String strippedToken = token.replaceAll("\"|bearer ", "");
        try{
            User user = new User(); //userService.findById(body.getUser());

            // compare user-provided token and actual token using message-digest, which
            // creates a constant time comparison, protecting against timing attacks
            byte[] expectedHash = hash(user).getBytes();
            byte[] actualHash = strippedToken.getBytes();

            return MessageDigest.isEqual(expectedHash, actualHash);

        } catch (Exception e){
            return false;
        }

    }

//    public boolean verifyLogin(User user) throws DataAccessException {
//        User expectedUser = userService.findByEmail(user);
//
//        if(expectedUser == null){
//            return false;
//        }
//
//
//    }

    public String hash(Object obj) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String objString = obj.toString();
        byte[] bytes = digest.digest(objString.getBytes());

        return Base64.getEncoder().encodeToString(bytes);
    }

    //TODO
    public String generateSalt(){
        return "";
    }

    //TODO
    public String saltAndHash(String value, String salt){
        return "";
    }

}