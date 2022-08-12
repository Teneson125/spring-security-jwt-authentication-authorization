package com.example.springsecurityjwtauthenticationauthorization.service;

import com.example.springsecurityjwtauthenticationauthorization.domain.Role;
import com.example.springsecurityjwtauthenticationauthorization.domain.User;
import com.example.springsecurityjwtauthenticationauthorization.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InitialService {

    @Autowired
    private UserServiceImp userServiceImp;

    public void initUser(){
        userServiceImp.saveUser(new User("manoj", "Teneson125", "kannamanoj2@gmail.com", "Manoj2@@@", true, true, true, true, new ArrayList<>()));
        userServiceImp.saveUser(new User("kanna", "Manoj125", "kannamanoj@gmail.com", "Kanna2@@@", true, true, true, true, new ArrayList<>()));
    }
    public void initRole(){
        userServiceImp.saveRole(new Role(null, "user"));
        userServiceImp.saveRole(new Role(null, "support"));
        userServiceImp.saveRole(new Role(null, "testing"));
        userServiceImp.saveRole(new Role(null, "admin"));
    }
    public void initRoleToUser(){
        userServiceImp.addRoleToUser("Teneson125", "admin");
        userServiceImp.addRoleToUser("Teneson125", "testing");
        userServiceImp.addRoleToUser("Teneson125", "support");
        userServiceImp.addRoleToUser("Manoj125", "user");
    }
}
