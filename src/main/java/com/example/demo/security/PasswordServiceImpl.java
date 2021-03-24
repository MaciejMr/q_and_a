package com.example.demo.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {

    public String hashPassword(String passwordPlaintext) {
        int workload = 12;
        String salt = BCrypt.gensalt(workload);
        return BCrypt.hashpw(passwordPlaintext, salt);
    }
}
