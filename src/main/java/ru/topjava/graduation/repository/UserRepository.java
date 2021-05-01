package ru.topjava.graduation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduation.model.entities.User;

import java.util.List;

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
        return crudUserRepository.findById(id).orElse(null);
    }

    @Transactional
    public User create(User user) {
        log.info("create new user");
        return crudUserRepository.save(user);
    }

    @Transactional
    public void deleteUser(int id) {
        log.info("delete user {}", id);
        crudUserRepository.deleteById(id);
    }
}
