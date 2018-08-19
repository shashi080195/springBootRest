package com.example.demo.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.example.demo.controller.UserNotFoundException;
import com.example.demo.models.Token;
import com.example.demo.models.UserResponse;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import com.example.demo.models.User;
import static com.example.demo.security.SecurityConstants.EXPIRATION_TIME;
import static com.example.demo.security.SecurityConstants.HEADER_STRING;
import static com.example.demo.security.SecurityConstants.TOKEN_PREFIX;
import static com.example.demo.security.SecurityConstants.SECRET;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDetailsServiceImpl userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsServiceImpl userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            com.example.demo.models.User creds = new ObjectMapper().readValue(req.getInputStream(),
                    com.example.demo.models.User.class);
            // Optional<com.example.demo.models.User> obj =
            // userRepository.findByUsername(creds.getUsername());
            // if (!obj.isPresent()) {
            // throw new UserNotFoundException("user not found-" + creds.getUsername());
            // }
            logger.info(String.valueOf(creds.getIsVerified()));
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
                    creds.getPassword(), new ArrayList<>()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        String username = ((User) auth.getPrincipal()).getUsername();
        logger.info(username);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        String token = JWT.create().withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).sign(HMAC512(SECRET.getBytes()));
        PrintWriter writer = res.getWriter();
        if (userService.isValidUser(username)) {
            Token tk = new Token();
            tk.setToken(TOKEN_PREFIX + token);
            Gson gson = new Gson();
            String jsonString = gson.toJson(tk);
            writer.print(jsonString);
        } else {
            UserResponse response = new UserResponse("0", "user emailId is not verified");
            Gson gson = new Gson();
            String jsonString = gson.toJson(response);
            writer.print(jsonString);
        }

    }
}