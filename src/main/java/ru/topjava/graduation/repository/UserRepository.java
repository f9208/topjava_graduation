package ru.topjava.graduation.repository;

import org.springframework.stereotype.Repository;
import ru.topjava.graduation.model.entities.User;

import java.util.List;

@Repository
public class UserRepository {
    private final CrudUserRepository crudUserRepository;

    public UserRepository(CrudUserRepository crudUserRepository) {
        this.crudUserRepository = crudUserRepository;
    }

    public List<User> getAll() {
        return crudUserRepository.findAll();
    }

    public User getOne(int id) {
        return crudUserRepository.findById(id).orElse(null);
    }
}
