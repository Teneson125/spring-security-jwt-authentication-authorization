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
        //if the servlet path is start with /api/ don't authorize, if not allow it for authorize
        //for /api/home anyone can access so no need to authorize
        // for /api/authenticate the user trying to log-in so no need to authorize
        if(request.getServletPath().startsWith("/api/")){
             filterChain.doFilter(request,response);
         }else {
             String authorizationHeader = request.getHeader(AUTHORIZATION);
             //checking the Authorization header is starting with Bearer and not null
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
                     //to check the role and authorize the person to access the api.
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
