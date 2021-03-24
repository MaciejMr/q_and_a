package com.example.demo.integration.util;

import com.example.demo.controller.UserController;
import com.example.demo.dto.RegisterNewUserDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisteredUserUtil {

    @Autowired
    private UserController userController;
    @Autowired
    private UserRepository userRepository;

    public User registerNewUserWith(String uniqueUserName) {
        return registerNewUserWith(uniqueUserName, "secure");
    }

    public User registerNewUserWith(String uniqueUserName, String password) {
        RegisterNewUserDTO dto = new RegisterNewUserDTO();
        String userName = uniqueUserName;
        String displayName = "displayNameFor" + uniqueUserName;
        String email = uniqueUserName + "@test.com";
        dto.setName(userName);
        dto.setDisplayName(displayName);
        dto.setEmail(email);
        dto.setPassword(password);
        userController.register(dto);
        return userRepository.findByName(uniqueUserName).get();
    }
}
