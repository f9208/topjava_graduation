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
import ru.topjava.graduation.repository.UserService;
import ru.topjava.graduation.utils.SecurityUtil;

import java.net.URI;

@RestController
@RequestMapping(value = ProfileController.PROFILE)
public class ProfileController {
    static final String PROFILE = "/profile";
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping()
    User getProfile() {
        return userRepository.findById(SecurityUtil.getAuthUserId());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
        }
        User created = userService.prepareAndSave(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(PROFILE + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProfile() {
        userRepository.delete(SecurityUtil.getAuthUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@RequestBody User user) {
        userRepository.update(user);
    }

    //    @PatchMapping

//    @GetMapping("/votes")


}
