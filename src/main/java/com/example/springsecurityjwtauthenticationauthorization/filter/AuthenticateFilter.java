package com.example.springsecurityjwtauthenticationauthorization.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springsecurityjwtauthenticationauthorization.domain.Role;
import com.example.springsecurityjwtauthenticationauthorization.domain.User;
import com.example.springsecurityjwtauthenticationauthorization.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

@Configuration
public class AuthenticateFilter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserServiceImp userServiceImp;


    public HashMap<String, String> authentication(String userName, String password) throws Exception {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
            authenticationManager.authenticate(authenticationToken);
        }catch(Exception e){
            HashMap<String, String> token = new HashMap<>();
            token.put("error","Invalid credentials");
            return token;
        }

        return successfulAuthentication(userServiceImp.getUser(userName));
    }

    private HashMap<String, String> successfulAuthentication(User user) throws Exception {


        Algorithm algorithm = Algorithm.HMAC256("manojkanna");

        String accessToken = JWT.create()
                .withSubject(user.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                //map(role -> role.getName()) = map(Role::getName)
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .withClaim("tokenName","access")
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .withClaim("tokenName","refresh")
                .sign(algorithm);


        HashMap<String , String> token = new HashMap<>();
        token.put("accessToken",accessToken);
        token.put("refreshToken",refreshToken);

        return token;
    }
}
