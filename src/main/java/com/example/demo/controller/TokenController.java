package com.example.demo.controller;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProviderImpl jwtTokenProvider;
    private UserRepository users;

    @Autowired
    public TokenController(AuthenticationManager authenticationManager, JwtTokenProviderImpl jwtTokenProvider, UserRepository users) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.users = users;
    }

    @PostMapping()
    public ResponseEntity<String> signin(@RequestBody AuthenticationRequest data) {
        String username = data.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
        String token = jwtTokenProvider.createToken(username, this.users.findByName(username).orElseThrow().getRoles());
        return ok(token);
    }
}

