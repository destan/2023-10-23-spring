package com.example.demo.security;

import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemoSecurityService {

    private final PasswordLeakChecker passwordLeakChecker;

    //@PreAuthorize("hasAuthority('ADMIN')")
    //@PostAuthorize("returnObject == 'hello'")

    @Secured("ROLE_ADMIN") // == @PreAuthorize("hasRole('ADMIN')")
    @PostAuthorize("@passwordLeakChecker.passwordSafe(returnObject)")
    User hello(User user) {
        return user;
    }

}
