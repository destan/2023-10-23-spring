package com.example.demo.post;

import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    ResponseEntity<Post> create(@RequestBody Post post, @AuthenticationPrincipal User user, Principal principal) {

        final Post newPost = postService.create(post, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @GetMapping("{id}")
    ResponseEntity<Post> detail(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @GetMapping
    ResponseEntity<List<Post>> list(@AuthenticationPrincipal Object auth) {
        log.info("Authentication {}", auth);
        return ResponseEntity.ok(postService.list());
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
