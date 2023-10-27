package com.example.demo.jwt_security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
class InvalidJwtStore {

    private final Set<String> invalidTokens = Collections.synchronizedSet(new HashSet<>());

    void verify(String token) {
        if (invalidTokens.contains(token)) {
            throw new JWTVerificationException("Already invalidated!");
        }
    }

    void invalidateToken(String token) {
        invalidTokens.add(token);
    }

}
