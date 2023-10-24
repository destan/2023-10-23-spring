package com.example.demo.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class UserService {

    private final UserRepository userRepository;

    private final UserDao userDao;

    public User create(User user) {
        return userRepository.save(user);
    }

    Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAllDto(pageable);
        //return userRepository.findAllByDeleted(false, pageable);
    }

    Optional<User> findById(Long id) {
        return userRepository.findByIdWithPosts(id);
    }

    void replace(Long id, User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @Transactional
    public void updateEmail(Long id, String email) {
        final Optional<User> optionalUser = userRepository.findById(id);
        final User user = optionalUser.get(); // FIXME throw exception to give 404

        user.setEmail(email);
        // userRepository.save(user); // If @Transactional is missing then we have to explicitly save the entity
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void softDelete(Long id) {
        final Optional<User> optionalUser = userRepository.findById(id);
        final User user = optionalUser.get(); // FIXME throw exception to give 404
        user.setDeleted(true);
    }

    public List<User> search(String searchTerm) {
        return userDao.search(searchTerm);
    }
}
