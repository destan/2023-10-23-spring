package com.example.demo.security;

import com.example.demo.user.User;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
//@RolesAllowed("ROLE_ADMIN")
//@Secured("ROLE_ADMIN")
@RequiredArgsConstructor
@RequestMapping({"security", "security/"})
class DemoSecurityController {

    private final DemoSecurityService demoSecurityService;

    @GetMapping
    ResponseEntity<UserDetails> userDetails(@AuthenticationPrincipal User user, Principal principal) {

        System.out.println(user);

        System.out.println(demoSecurityService.hello(user));

        return ResponseEntity.ok(user);
    }

}
