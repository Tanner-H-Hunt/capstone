package com.dev10.controllers;

import com.dev10.models.DataAccessException;
import com.dev10.models.DTO.ResourceRequest;
import com.dev10.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class Authenticator {

    @Autowired
    private Environment env;


    /**
     * Determine whether a user is allowed to make an action
     * @param expectedUser the user that exists in the database with the credentials sent by the user
     * @param token the bearer token on this transaction
     * @return true if the auth bearer token belongs to this user
     */
    public boolean isValidBearerToken(User expectedUser, String token){
        if(expectedUser == null || token == null || token.isBlank()){
            return false;
        }

        String strippedToken = token.replaceAll("\"|bearer ", "");
        String secret = env.getProperty("security_hash_value");

        try{

            // compare user-provided token and actual token using message-digest, which
            // creates a constant time comparison, protecting against timing attacks
            byte[] expectedHash = hash(expectedUser + secret).getBytes();
            byte[] actualHash = strippedToken.getBytes();

            return MessageDigest.isEqual(expectedHash, actualHash);

        } catch (Exception e){
            return false;
        }

    }

    public boolean isUserPermitted(User user, ResourceRequest request){
        // could not match this resource with any users (resource does not exist)
        if(request.getUser() == null){
            request.clear();
            return false;
        }

        boolean isPermitted = request.getUser().equals(user);
        request.clear();
        return isPermitted;
    }

    /**
     * Compare a users provided password with the salted and hashed password from the database
     * @param user the provided user credentials
     * @return true if the passwords match
     * @throws DataAccessException If there was an error reading data from the database
     * @throws NoSuchAlgorithmException .
     */
    public boolean verifyLogin(User user, User expectedUser) throws DataAccessException, NoSuchAlgorithmException {
        if(expectedUser == null){
            return false;
        }

        // get the already salted and hashed password from the database
        byte[] expectedPassword = expectedUser.getPassword().getBytes();
        // salt and hash the actual password with the salt from the database for this email address
        byte[] actualPassword = saltAndHash(user.getPassword(), expectedUser.getSalt()).getBytes();

        // comparisons with MessageDigest is more resilient to timing attacks than String.isEqual();
        return MessageDigest.isEqual(expectedPassword, actualPassword);
    }

    /**
     * Salt and hash a password before it is sent to the database
     * @param user the newly created user data
     * @return the user with sensitive information hashed and salted
     * @throws NoSuchAlgorithmException .
     */
    public void makeAccountDetailsSecure(User user) throws NoSuchAlgorithmException {
        // hashing a blank password hides blank passwords from the User service
        if(user.getPassword() == null || user.getPassword().isBlank()){
            return;
        }

        String salt = generateSalt();
        user.setSalt(salt);

        String saltedAndHashedPassword = saltAndHash(user.getPassword(), salt);
        user.setPassword(saltedAndHashedPassword);
    }

    public String generateBearerToken(User user) throws NoSuchAlgorithmException {
        String secret = env.getProperty("security_hash_value");
        return hash(user + secret);
    }

    /**
     * Create a secure sha-256 encryption of an objects toString() output
     * @param obj the item to be hashed
     * @return A string hash representing the parameter object
     * @throws NoSuchAlgorithmException .
     */
    public String hash(Object obj) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String objString = obj.toString();
        byte[] bytes = digest.digest(objString.getBytes());

        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Generate a secure, random salt for database passwords
     * @return a string of 16 random bytes encoded into base 64
     */
    public String generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Does what it says on the tin: salt and hash a password
     * @param value the password to be salted
     * @param salt the random bytes to append to the password
     * @return the encrypted password
     * @throws NoSuchAlgorithmException .
     */
    public String saltAndHash(String value, String salt) throws NoSuchAlgorithmException {
        return hash(value + salt);
    }

}