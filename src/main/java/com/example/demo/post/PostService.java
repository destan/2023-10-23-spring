package com.example.demo.post;

import com.example.demo.exception.BusinessLogicException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private static final CharSequence BAD_WORD = "lorem";

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    Post create(Post post, Long userId) {

        if (post.getContent().toLowerCase().contains(BAD_WORD)) {
            throw new BusinessLogicException("Post cannot include a bad word!");
        }

        final User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(userId, User.class));

        post.setOwner(user);

        return postRepository.save(post);
    }

    public List<Post> list() {
        return postRepository.findAll(); //TODO pageable
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, Post.class));
    }

    public Post replace(Post post) {
        return postRepository.save(post);
    }
}
