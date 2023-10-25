package com.example.demo.user;

import com.example.demo.validation.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
class UserController {

    private final UserService userService;

    private final UserValidator userValidator;

    @InitBinder
    void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userValidator);
        // webDataBinder.addValidators(new UserValidator());
    }

    @PostMapping
    ResponseEntity<User> create(@RequestBody @Valid User user, BindingResult bindingResult) {

        // if (bindingResult.hasErrors()) {
        //     bindingResult.getAllErrors().forEach(objectError -> log.warn(objectError.getObjectName() + " " + objectError.getDefaultMessage()));
        //     return ResponseEntity.badRequest().build();
        // }

        final User createdUser = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping
    ResponseEntity<Page<UserDto>> list(Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("{id}")
    ResponseEntity<User> detail(@PathVariable("id") Long id) {
        final User user = userService.findById(id);
        return ResponseEntity.ok(user);
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
        } else {
            userService.softDelete(id);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("search")
    ResponseEntity<List<User>> search(@RequestParam("searchTerm") String searchTerm) {
        return ResponseEntity.ok(userService.search(searchTerm));
    }

    // @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Email already exists!")
    // @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    // public void conflict(SQLIntegrityConstraintViolationException e) {
    //     log.error("Hata mesaji: " + e.getMessage(), e);
    // }

    record UserUpdateRequest(String email) {

    }
}
