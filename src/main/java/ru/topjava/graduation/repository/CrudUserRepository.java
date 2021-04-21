package ru.topjava.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.topjava.graduation.model.entities.User;

public interface CrudUserRepository extends JpaRepository<User, Integer> {


}
