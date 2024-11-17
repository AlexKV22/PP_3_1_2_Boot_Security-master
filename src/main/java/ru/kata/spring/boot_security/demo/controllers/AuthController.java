package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.JwtRequest;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.utils.JWTTokenUtils;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTokenUtils jwtTokenUtils;


    public AuthController(UserService userService, JWTTokenUtils jwtTokenUtils) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @PostMapping()
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest jwtRequest) {


    }
}
