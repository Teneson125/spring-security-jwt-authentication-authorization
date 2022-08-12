package com.example.springsecurityjwtauthenticationauthorization.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         if(request.getServletPath().startsWith("/api/")){
             filterChain.doFilter(request,response);
         }else {
             String authorizationHeader = request.getHeader(AUTHORIZATION);

             if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                 try {
                     String accessToken = authorizationHeader.substring("Bearer ".length());
                     Algorithm algorithm = Algorithm.HMAC256("manojkanna");
                     JWTVerifier verifier = JWT.require(algorithm).build();
                     DecodedJWT decodedJWT = verifier.verify(accessToken);

                     String userName = decodedJWT.getSubject();
                     String [] roles = decodedJWT.getClaim("roles").asArray(String.class);
                     Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                     stream(roles).forEach(role-> {
                         authorities.add(new SimpleGrantedAuthority(role));
                     });

                     UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, authorities);
                     SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                     filterChain.doFilter(request,response);
                 }catch (Exception e){
                     response.setStatus(FORBIDDEN.value());
                     HashMap<String , String> message = new HashMap<>();
                     message.put("error", e.getMessage());
                     response.setContentType(APPLICATION_JSON_VALUE);
                     new ObjectMapper().writeValue(response.getOutputStream(), message);
                 }
             }else {
                 response.setStatus(FORBIDDEN.value());
                 HashMap<String , String> message = new HashMap<>();
                 message.put("error", "token needed");
                 response.setContentType(APPLICATION_JSON_VALUE);
                 new ObjectMapper().writeValue(response.getOutputStream(), message);
             }
         }
    }
}
