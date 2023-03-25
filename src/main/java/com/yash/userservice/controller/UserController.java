package com.yash.userservice.controller;

import com.yash.userservice.dto.UserDTO;
import com.yash.userservice.entities.User;
import com.yash.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody @Valid UserDTO user){
        User user1 = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        return  ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserByID(@PathVariable String userId){
        User user = userService.getUserByID(userId);
        return  ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable String userId){
        userService.deleteUserByID(userId);
        return ResponseEntity.accepted().build();
    }

}
