package com.example.demo.controller;

import com.example.demo.dto.RegisterNewUserDTO;
import com.example.demo.service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private RegisterUserService registerUserService;

    @Autowired
    public UserController(RegisterUserService registerUserService) {
        this.registerUserService = registerUserService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> register(@RequestBody RegisterNewUserDTO registerNewUserDTO) {
        registerUserService.registerNewUser(registerNewUserDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
