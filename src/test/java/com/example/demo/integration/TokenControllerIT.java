package com.example.demo.integration;

import com.example.demo.controller.TokenController;
import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.integration.util.RegisteredUserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
class TokenControllerIT {

    @Autowired
    private TokenController sut;

    @Autowired
    private RegisteredUserUtil registeredUserCreator;

    @Test
    void shouldReturnToken_WhenUserIsRegistered() {
        // given
        String username = "Carolina";
        String pass = "us state";
        registeredUserCreator.registerNewUserWith(username, pass);
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(username);
        request.setPassword(pass);

        // when
        ResponseEntity<String> result = sut.signin(request);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertFalse(result.getBody().isEmpty());
    }
}