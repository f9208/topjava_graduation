package ru.topjava.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.topjava.graduation.model.entities.Role;
import ru.topjava.graduation.model.entities.User;
import ru.topjava.graduation.model.entities.to.VoteTo;
import ru.topjava.graduation.repository.UserRepository;
import ru.topjava.graduation.repository.VoteRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static ru.topjava.graduation.model.entities.to.VoteTo.convert;

@RestController
@RequestMapping(AdminUserController.ADMIN_USERS)
public class AdminUserController {
    static final String ADMIN_USERS = "/admin/users";
    @Autowired
    UserRepository userRepository;
    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return userRepository.findById(id);
    }

    @GetMapping
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @GetMapping("/{id}/votes")
    public List<VoteTo> getVotesForUser(@PathVariable("id") int userId) {
        return convert(voteRepository.getAllForUser(userId));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestBody int id) {
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
