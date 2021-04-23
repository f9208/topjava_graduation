package ru.topjava.graduation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
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

    public User getOne(int id) {
        log.info("get user {}", id);
        return crudUserRepository.findById(id).orElse(null);
    }
}
