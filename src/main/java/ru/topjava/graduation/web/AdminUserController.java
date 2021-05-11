package ru.topjava.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.graduation.model.entities.Role;
import ru.topjava.graduation.model.entities.User;
import ru.topjava.graduation.repository.UserRepository;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(AdminUserController.ADMIN_USERS)
public class AdminUserController {
    static final String ADMIN_USERS = "/admin/users";
    @Autowired
    UserRepository userRepository;

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return userRepository.findById(id);
    }

    @GetMapping
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userRepository.delete(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) {
        userRepository.update(user);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeRoles(@RequestParam("role") Set<Role> role, @RequestParam("id") int userId) {
        userRepository.update(userId, role);
    }
}
