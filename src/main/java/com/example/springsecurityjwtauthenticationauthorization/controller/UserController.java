package com.example.springsecurityjwtauthenticationauthorization.controller;

import com.example.springsecurityjwtauthenticationauthorization.configuration.Token;
import com.example.springsecurityjwtauthenticationauthorization.domain.Role;
import com.example.springsecurityjwtauthenticationauthorization.domain.RoleToUser;
import com.example.springsecurityjwtauthenticationauthorization.domain.User;
import com.example.springsecurityjwtauthenticationauthorization.filter.AuthenticateFilter;
import com.example.springsecurityjwtauthenticationauthorization.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Configuration
public class UserController {

    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    private AuthenticateFilter authenticateFilter;
    @Autowired
    private Token token;

    @RequestMapping("/api/")
    @RestController
    class all{
        @GetMapping("home")
        public String index(){
            return "index page";
        }
        @PostMapping("authenticate")
        public HashMap<String, String> authenticate(@RequestParam String userName, @RequestParam String password) throws Exception {
            return authenticateFilter.authentication(userName, password);
        }
        @PostMapping("accessToken")
        public HashMap<String, String> accessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
            return token.accessToken(request,response);
        }
    }

    @RequestMapping("/api1/")
    @RestController
    static
    class user {
        @GetMapping("home")
        public String index(){
            return "user page";
        }
    }

    @RequestMapping("/api2/")
    @RestController
    static
    class support {
        @GetMapping("home")
        public String index(){
            return "support page";
        }
    }

    @RequestMapping("/api3/")
    @RestController
    static
    class testing {
        @GetMapping("home")
        public String index(){
            return "testing page";
        }
    }

    @RequestMapping("/api4/")
    @RestController
    class admin {
        @GetMapping("home")
        public String index(){
            return "admin page";
        }
        @GetMapping("users")
        public ResponseEntity<List<User>> getUsers(){
            return ResponseEntity.ok().body(userServiceImp.getUsers());
        }
        @PostMapping("user/save")
        public ResponseEntity<User> saveUser(@RequestBody User user){
            return ResponseEntity.ok().body(userServiceImp.saveUser(user));
        }
        @GetMapping("roles")
        public ResponseEntity<List<Role>> getRoles(){
            return ResponseEntity.ok().body(userServiceImp.getRoles());
        }
        @PostMapping("role/save")
        public ResponseEntity<Role> saveRole(@RequestBody Role role){
            return ResponseEntity.ok().body(userServiceImp.saveRole(role));
        }
        @PostMapping("role/add_role_to_user")
        public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUser roleToUser){
            userServiceImp.addRoleToUser(roleToUser.getUserName(),roleToUser.getRoleName());
            return ResponseEntity.ok().build();
        }
    }


}