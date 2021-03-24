package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.dto.RegisterNewUserDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    private UserController sut;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldRegisterNewUser_WhenAllInformationProvidedAndUnique() {
        // given
        RegisterNewUserDTO dto = new RegisterNewUserDTO();
        String userName = "userName";
        String displayName = "displayName";
        String email = "email@test.com";
        String password = "secure";
        dto.setName(userName);
        dto.setDisplayName(displayName);
        dto.setEmail(email);
        dto.setPassword(password);

        // when
        ResponseEntity<Void> result = sut.register(dto);

        //then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Optional<User> userOptional = userRepository.findByName(userName);
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();
        assertEquals(userName, user.getUsername());
        assertEquals(displayName, user.getDisplayName());
        assertEquals(email, user.getEmail());
    }

}
