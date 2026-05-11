package com.dev10.controllers;

import com.dev10.domain.UserService;
import com.dev10.models.DataAccessException;
import com.dev10.models.Result;
import com.dev10.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody User user) throws DataAccessException {

        User foundUser = userService.findByEmail(user);

        if(foundUser == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody User user) throws DataAccessException {
        Result<User> result = userService.createAccount(user);

        if(result.isSuccess()){
            return ResponseEntity.ok().body(result.getPayload());
        }

        return ResponseEntity.badRequest().body(result.getErrorMessages());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable("userId") int id) throws DataAccessException {
        User user = userService.findById(id);

        if(user == null){
            return ResponseEntity.notFound().build();
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }
}
