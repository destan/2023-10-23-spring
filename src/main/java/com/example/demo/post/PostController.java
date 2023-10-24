package com.example.demo.post;

import com.example.demo.user.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    ResponseEntity<Post> create(@RequestBody Post post, @RequestParam("userId") Long userId) {
        final Post newPost = postService.create(post, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @GetMapping("{id}")
    ResponseEntity<Post> detail(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

}
