package ru.topjava.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduation.model.entities.Dish;

import java.util.List;


@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {
    @Override
    Dish save(Dish dish);

    boolean deleteDishById(Integer id);

    Dish findDishById(Integer id);

    List<Dish> findAll();

}
