package ru.topjava.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.topjava.graduation.model.entities.User;
import ru.topjava.graduation.repository.UserRepository;
import ru.topjava.graduation.utils.SecurityUtil;

@RestController
@RequestMapping(value = ProfileController.HEAD_URL)
public class ProfileController {
    static final String HEAD_URL = "/profile";
    @Autowired
    UserRepository userRepository;

    @GetMapping()
    User getProfile() {
        return userRepository.findById(SecurityUtil.getAuthUser().getId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    User createProfile(@RequestBody User user) {
        return userRepository.create(user);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteYourself(@RequestBody int id) {
        if (SecurityUtil.getAuthUser().getId() == id) {
            userRepository.delete(id);
        }
    }

//    @PutMapping
//    @PatchMapping

}
