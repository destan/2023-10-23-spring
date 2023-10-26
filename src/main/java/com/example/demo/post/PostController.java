package com.example.demo.post;

import com.example.demo.session.DemoSessionController;
import com.example.demo.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    ResponseEntity<Post> create(@RequestBody Post post, HttpSession session) {

        final Object userFromSession = session.getAttribute(DemoSessionController.SESSION_USER_KEY);

        if (userFromSession instanceof User user) {
            final Post newPost = postService.create(post, user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("{id}")
    ResponseEntity<Post> detail(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @PutMapping("{id}")
    ResponseEntity<Post> replace(@PathVariable Long id, @RequestBody Post post) {

        post.setId(id);

        return ResponseEntity.ok(postService.replace(post));
    }

    // @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Email already exists!")
    // @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    // public void conflict(SQLIntegrityConstraintViolationException e) {
    //     log.error("Hata mesaji: " + e.getMessage(), e);
    // }
}
