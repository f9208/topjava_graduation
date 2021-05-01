package ru.topjava.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.graduation.model.entities.User;
import ru.topjava.graduation.repository.UserRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(AdminUserController.ADMIN_USERS)
public class AdminUserController {
    static final String ADMIN_USERS = "/admin/users";
    @Autowired
    UserRepository userRepository;

    @GetMapping("/{id}")
    User get(@PathVariable int id) {
        return userRepository.findById(id);
    }

    @GetMapping
    List<User> getAll() {
        return userRepository.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(User user, BindingResult result) {
        if (result.hasErrors()) {
        }
        User created = userRepository.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_USERS + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@RequestBody int id) {
        userRepository.delete(id);
    }
}
