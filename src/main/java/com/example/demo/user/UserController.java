package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
class UserController {

    private final UserService userService;

    @PostMapping
    ResponseEntity<User> create(@RequestBody User user) {
        //FIXME return 409 instead 500 when JdbcSQLIntegrityConstraintViolationException
        final User createdUser = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping
    ResponseEntity<Page<UserDto>> list(Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("{id}")
    ResponseEntity<User> detail(@PathVariable("id") Long id) {
        final Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalUser.get());

        //return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    ResponseEntity<Void> replace(@PathVariable("id") Long id, @RequestBody User user) {

        userService.replace(id, user);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}")
    ResponseEntity<Void> updateEmail(@PathVariable("id") Long id, @RequestBody UserUpdateRequest request) {

        userService.updateEmail(id, request.email());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id, @RequestParam(value = "hard", defaultValue = "false") boolean isHard) {

        if (isHard) {
            userService.delete(id);
        }
        else {
            userService.softDelete(id);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("search")
    ResponseEntity<List<User>> search(@RequestParam("searchTerm") String searchTerm) {
        return ResponseEntity.ok(userService.search(searchTerm));
    }

    record UserUpdateRequest(String email) {

    }
}
