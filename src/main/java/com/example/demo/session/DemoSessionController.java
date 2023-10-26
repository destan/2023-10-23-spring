package com.example.demo.session;

import com.example.demo.user.User;
import com.example.demo.user.UserDto;
import com.example.demo.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("session")
@RequiredArgsConstructor
public class DemoSessionController {

    private final static String SESSION_KEY = "time";

    public final static String SESSION_USER_KEY = "user";

    private final UserRepository userRepository;

    @GetMapping
    ResponseEntity<String> session(HttpSession httpSession) {

        final Object timeFromSession = httpSession.getAttribute(SESSION_KEY);

        System.out.println("==============%s===============".formatted(httpSession.getId()));
        System.out.println(httpSession.getMaxInactiveInterval());
        System.out.println(timeFromSession);
        System.out.println("=============================");

        if (timeFromSession == null) {
            httpSession.setAttribute(SESSION_KEY, Instant.now());
        }

        return ResponseEntity.ok(timeFromSession + " \ndone");
    }

    @GetMapping("delete")
    ResponseEntity<String> invalidate(HttpSession httpSession) {

        httpSession.invalidate();

        return ResponseEntity.ok("deleted");
    }

    @GetMapping("login")
    ResponseEntity<String> fakeLogin(@RequestParam String email, HttpSession httpSession) {

        final User user = userRepository.findByEmail(email).orElse(null);

        httpSession.setAttribute(SESSION_USER_KEY, user);

        return ResponseEntity.ok("logged in");
    }

}
