package com.example.demo.security;

import com.example.demo.user.User;
import org.springframework.stereotype.Component;

@Component
class PasswordLeakChecker {

    public boolean passwordSafe(User user) {
        return user == null || user.getPassword() == null || user.getPassword().isEmpty();
    }

}
