package com.example.springsecurityjwtauthenticationauthorization.service;

import com.example.springsecurityjwtauthenticationauthorization.domain.Role;
import com.example.springsecurityjwtauthenticationauthorization.domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String userName, String roleName);
    User getUser(String userName);
    User getUserByEmail(String email);
    List<User> getUsers();
}
