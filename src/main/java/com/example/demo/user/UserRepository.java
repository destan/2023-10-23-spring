package com.example.demo.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT new com.example.demo.user.UserDto(u.id, u.fullName, u.email) FROM User u", countQuery = "SELECT COUNT(*) FROM User")
    Page<UserDto> findAllDto(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.deleted = false")
    List<User> findAllNotDeleted();

    @Query(value = "SELECT * FROM app_user u where deleted = false", nativeQuery = true)
    List<User> findAllNotDeletedNative();


    Page<User> findAllByDeleted(boolean isDeleted, Pageable pageable);

    List<User> findAllByDeletedFalse();

    @Query("SELECT u FROM User u JOIN FETCH u.posts")
    Optional<User> findByIdWithPosts(Long aLong);
}
