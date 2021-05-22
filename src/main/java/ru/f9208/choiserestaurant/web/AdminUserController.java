package ru.f9208.choiserestaurant.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.f9208.choiserestaurant.model.entities.Role;
import ru.f9208.choiserestaurant.model.entities.User;
import ru.f9208.choiserestaurant.model.entities.to.VoteTo;
import ru.f9208.choiserestaurant.repository.UserRepository;
import ru.f9208.choiserestaurant.repository.VoteRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static ru.f9208.choiserestaurant.model.entities.to.VoteTo.convert;

@RestController
@RequestMapping(AdminUserController.ADMIN_USERS)
public class AdminUserController {
    public static final String ADMIN_USERS = "/admin/users";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoteRepository voteRepository;

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return userRepository.findById(id);
    }

    @GetMapping
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userRepository.delete(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user) {
        userRepository.update(user);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeRoles(@RequestParam("role") Set<Role> role, @RequestParam("id") int userId) {
        userRepository.update(userId, role);
    }
}
