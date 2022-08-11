package com.example.springsecurityjwtauthenticationauthorization.controller;

import com.example.springsecurityjwtauthenticationauthorization.domain.User;
import com.example.springsecurityjwtauthenticationauthorization.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserServiceImp userServiceImp;

    @GetMapping("users")
    private ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(userServiceImp.getUsers());
    }
}