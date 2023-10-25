package com.example.demo.user;

import com.example.demo.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserDao userDao;

    public User create(User user) {
        return userRepository.save(user);
    }

    Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAllDto(pageable);
        //return userRepository.findAllByDeleted(false, pageable);
    }

    User findById(Long id) {
        return userRepository.findByIdWithPosts(id).orElseThrow(() -> new ResourceNotFoundException(id, User.class));
    }

    void replace(Long id, User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @Transactional
    public void updateEmail(Long id, String email) {
        final User user = userRepository
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(id, User.class));

        user.setEmail(email);
        // userRepository.save(user); // If @Transactional is missing then we have to explicitly save the entity
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void softDelete(Long id) {
        final User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, User.class));
        user.setDeleted(true);
    }

    public List<User> search(String searchTerm) {
        return userDao.search(searchTerm);
    }
}
