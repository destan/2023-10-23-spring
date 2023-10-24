package com.example.demo.post;

import com.example.demo.user.Post;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    Post create(Post post, Long userId) {

        final Optional<User> optionalUser = userRepository.findById(userId);
        final User user = optionalUser.get(); // FIXME

        post.setOwner(user);

        return postRepository.save(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).get();//FIXME
    }
}
