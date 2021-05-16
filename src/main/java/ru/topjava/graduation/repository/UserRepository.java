package ru.topjava.graduation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.topjava.graduation.model.entities.Role;
import ru.topjava.graduation.model.entities.User;

import java.util.List;
import java.util.Set;

import static ru.topjava.graduation.utils.ValidatorUtil.*;

@Repository
public class UserRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final CrudUserRepository crudUserRepository;

    public UserRepository(CrudUserRepository crudUserRepository) {
        this.crudUserRepository = crudUserRepository;
    }

    public List<User> getAll() {
        log.info("getAll users");
        return crudUserRepository.findAll();
    }

    public User findById(int id) {
        log.info("get user {}", id);
        return checkNotFound(crudUserRepository.findById(id).orElse(null), String.valueOf(id));
    }

    @Transactional
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        checkNew(user);
        log.info("create new user");
        return crudUserRepository.save(user);
    }

    @Transactional
    public boolean delete(int id) {
        log.info("delete user {}", id);
        boolean result = crudUserRepository.delete(id) != 0;
        checkNotFoundWithId(result, id);
        return result;
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        log.info("get user by email {}", email);
        return crudUserRepository.findByEmail(email);
    }

    @Transactional
    public User update(User user) {
        Assert.notNull(user, "user must not be null");
        log.info("update user with id {}", user.getId());
        return crudUserRepository.save(user);
    }

    @Transactional
    public User update(int userId, Set<Role> role) {
        log.info("update Role for user with id {}. New roles: {}", userId, role);
        User result = crudUserRepository.findById(userId).orElseThrow();
        result.setRoles(role);
        return result;
    }

    @Transactional
    public User save(User user) {
        return crudUserRepository.save(user);
    }
}
