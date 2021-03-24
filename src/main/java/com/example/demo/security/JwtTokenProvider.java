package com.example.demo.security;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface JwtTokenProvider {

    String createToken(String username, List<String> roles);

    Authentication getAuthentication(String token);

    String getUsername(String token);

    String resolveToken(HttpServletRequest req);

    boolean validateToken(String token);

}

