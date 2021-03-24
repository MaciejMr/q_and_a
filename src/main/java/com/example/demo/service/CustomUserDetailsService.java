package com.example.demo.service;

import com.example.demo.dto.RegisterNewUserDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.PasswordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService, RegisterUserService {
    private UserRepository users;
    private PasswordServiceImpl passwordService;

    @Autowired
    public CustomUserDetailsService(UserRepository users, PasswordServiceImpl passwordService) {
        this.users = users;
        this.passwordService = passwordService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.users.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }

    @Override
    public void registerNewUser(RegisterNewUserDTO registerNewUserDTO) {
        String hashedPassword = passwordService.hashPassword(registerNewUserDTO.getPassword());
        User user = new User(registerNewUserDTO.getName(), hashedPassword, registerNewUserDTO.getEmail(), registerNewUserDTO.getDisplayName());
        users.save(user);
    }
}
