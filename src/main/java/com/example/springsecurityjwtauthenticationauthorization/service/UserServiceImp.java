package com.example.springsecurityjwtauthenticationauthorization.service;

import com.example.springsecurityjwtauthenticationauthorization.domain.Role;
import com.example.springsecurityjwtauthenticationauthorization.domain.User;
import com.example.springsecurityjwtauthenticationauthorization.repository.RoleRepository;
import com.example.springsecurityjwtauthenticationauthorization.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        User user = userRepository.findByUserName(userName);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}