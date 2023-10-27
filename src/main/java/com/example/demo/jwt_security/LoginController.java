package com.example.demo.jwt_security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
class LoginController {

    private final AuthenticationManager authenticationManager;

    private final InvalidJwtStore invalidJwtStore;

    @PostMapping("login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

            final User user = (User) authentication.getPrincipal();

            Algorithm algorithm = Algorithm.HMAC256("c56222fa88ed489daae109e38d65afd0"); //FIXME
            final Instant now = Instant.now();
            final Instant expiresAt = now.plus(1, ChronoUnit.HOURS);
            String token = JWT.create()
                    .withJWTId(UUID.randomUUID().toString())
                    .withIssuer("api")
                    .withAudience("spring-course")
                    .withSubject(loginRequest.username())
                    .withExpiresAt(expiresAt)
                    .withIssuedAt(now)
                    .withClaim("uid", user.getId())
                    .sign(algorithm);

            return ResponseEntity.ok(new LoginResponse(token, expiresAt.toEpochMilli()));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("logout")
    ResponseEntity<Void> logout(@AuthenticationPrincipal User user, @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {

        if (user != null) {
            invalidJwtStore.invalidateToken(jwt.substring(7));
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    record LoginRequest(String username, String password) { }
    record LoginResponse(String token, long expiresAt) { }

}
