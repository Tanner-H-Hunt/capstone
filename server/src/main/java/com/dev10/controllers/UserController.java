package com.dev10.controllers;

import com.dev10.domain.UserService;
import com.dev10.models.DataAccessException;
import com.dev10.models.DTO.Result;
import com.dev10.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final Authenticator authenticator;

    public UserController(UserService userService, Authenticator authenticator){
        this.userService = userService;
        this.authenticator = authenticator;
    }

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody User user) throws DataAccessException, NoSuchAlgorithmException {
        User foundUser = userService.findByEmail(user);

        if(foundUser == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Incorrect email or password");
        }

//        if(!authenticator.verifyLogin(user, foundUser)){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email or password");
//        }
//        String response = String.format("{\"user\": %s, \"bearer_token\": \"%s\"}", foundUser, authenticator.generateBearerToken(foundUser));
        return ResponseEntity.status(HttpStatus.OK).body("COME UPDATE ME!!"); //TODO update the response
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody User user) throws DataAccessException, NoSuchAlgorithmException {

//        authenticator.makeAccountDetailsSecure(user);

        Result<User> result = userService.createAccount(user);

        if(result.isSuccess()){
//            String response = String.format("{\"user\": %s, \"bearer_token\": \"%s\"}",
//                    result.getPayload(),
//                    authenticator.generateBearerToken(result.getPayload()));
            return ResponseEntity.ok().body("COME UPDATE ME"); //TODO update the response
        }

        return ResponseEntity.badRequest().body(result.getErrorMessages());
    }

}
