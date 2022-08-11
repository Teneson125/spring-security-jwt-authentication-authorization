package com.example.springsecurityjwtauthenticationauthorization.controller;

import com.example.springsecurityjwtauthenticationauthorization.service.InitialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InitialController {

    @Autowired
    private InitialService initialService;

    @GetMapping("init_user")
    public void initUser(){
        initialService.initUser();
    }
    @GetMapping("init_role")
    public void initRole(){
        initialService.initRole();
    }
    @GetMapping("init_role_to_user")
    public void initRoleToUser(){
        initialService.initRoleToUser();
    }

}
