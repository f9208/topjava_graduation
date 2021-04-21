package ru.topjava.graduation.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.topjava.graduation.model.entities.User;

@RestController
@RequestMapping(value = UserController.HEAD_URL)
public class UserController {
    static final String HEAD_URL = "/users";

    @GetMapping("/{id}")
    User get(@PathVariable int id) {
        return null;
    }
}
