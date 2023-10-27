package com.example.demo.jwt_security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Algorithm algorithm = Algorithm.HMAC256("c56222fa88ed489daae109e38d65afd0");
        final Instant now = Instant.now();
        String token = JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withIssuer("api")
                .withAudience("spring-course")
                .withSubject(authentication.getName())
                .withExpiresAt(now.plus(1, ChronoUnit.HOURS))
                .withIssuedAt(now)
                .sign(algorithm);

        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
        response.getWriter().flush();
    }
}
