package com.example.demo.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("security")
class DemoSecurityController {

    @GetMapping
    ResponseEntity<String> userDetails(@AuthenticationPrincipal UserDetails userDetails, HttpSession httpSession) {

        System.out.println(userDetails);

        return ResponseEntity.ok(userDetails.getAuthorities().toString());
    }

}
