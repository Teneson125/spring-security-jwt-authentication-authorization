package com.example.springsecurityjwtauthenticationauthorization.controller;

import com.example.springsecurityjwtauthenticationauthorization.service.InitialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;


@RestController
public class InitialController {

    @Autowired
    private InitialService initialService;

    @PostConstruct
    public void initUser(){
        initialService.initUser();
        initialService.initRole();
        initialService.initRoleToUser();
    }


}
