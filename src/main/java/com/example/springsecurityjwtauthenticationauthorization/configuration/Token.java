package com.example.springsecurityjwtauthenticationauthorization.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springsecurityjwtauthenticationauthorization.domain.Role;
import com.example.springsecurityjwtauthenticationauthorization.domain.User;
import com.example.springsecurityjwtauthenticationauthorization.service.UserServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class Token {

    @Autowired
    private UserServiceImp userServiceImp;

    /* this method is to regenerate the access token by getting the refresh token
       when the refresh toke is null or not start with bearer then it will send error as "refresh token needed"
       and also it sent the error message if the token is invalid or expire.

     */
    public HashMap<String, String > accessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        //checking the Authorization header is starting with Bearer and not null
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());

                Algorithm algorithm = Algorithm.HMAC256("manojkanna");
                Algorithm algorithm2 = Algorithm.HMAC256("kannamanoj");
                JWTVerifier verifier = JWT.require(algorithm2).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);

                String userName = decodedJWT.getSubject();

                User user = userServiceImp.getUser(userName);

                String accessToken = JWT.create()
                        .withSubject(user.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                        //map(role -> role.getName()) = map(Role::getName)
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .withClaim("tokenName","access")
                        .sign(algorithm);

                HashMap<String , String> token = new HashMap<>();
                token.put("accessToken",accessToken);
                token.put("refreshToken",refreshToken);

                return token;
            }catch (Exception e){
                response.setStatus(FORBIDDEN.value());
                HashMap<String , String> message = new HashMap<>();
                message.put("error", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
                return message;
            }
        }else {
            response.setStatus(FORBIDDEN.value());
            HashMap<String , String> message = new HashMap<>();
            message.put("error", "refresh token needed");
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
            return message;
        }
    }
}
