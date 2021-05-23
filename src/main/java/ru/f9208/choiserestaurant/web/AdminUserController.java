package ru.f9208.choiserestaurant.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.f9208.choiserestaurant.model.entities.Role;
import ru.f9208.choiserestaurant.model.entities.User;
import ru.f9208.choiserestaurant.repository.UserRepository;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(AdminUserController.ADMIN_USERS)
public class AdminUserController {
    public static final String ADMIN_USERS = "/admin/users";
    @Autowired
    private UserRepository userRepository;

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
    public void delete(@PathVariable int id) {
        userRepository.delete(id);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeRoles(@RequestParam("role") Set<Role> role, @RequestParam("id") int userId) {
        userRepository.update(userId, role);
    }
}
