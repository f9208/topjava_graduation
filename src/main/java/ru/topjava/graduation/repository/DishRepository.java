package ru.topjava.graduation.repository;

import org.springframework.stereotype.Repository;
import ru.topjava.graduation.model.entities.Dish;

import java.util.List;

@Repository
public class DishRepository {
    private final CrudDishRepository crudDishRepository;

    public DishRepository(CrudDishRepository crudDishRepository) {
        this.crudDishRepository = crudDishRepository;
    }

    public Dish save(Dish dish) {
        return crudDishRepository.save(dish);
    }

    public Dish get(Integer id) {
        return crudDishRepository.findDishById(id);
    }

    public List<Dish> getAll() {
        return crudDishRepository.findAll();
    }
}
