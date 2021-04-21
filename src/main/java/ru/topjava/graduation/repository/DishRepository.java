package ru.topjava.graduation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.graduation.model.entities.Dish;

import java.util.List;

@Repository
public class DishRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final CrudDishRepository crudDishRepository;

    public DishRepository(CrudDishRepository crudDishRepository) {
        this.crudDishRepository = crudDishRepository;
    }

    @Transactional
    public Dish create(Dish dish) {
        log.info("save dish");
        return crudDishRepository.save(dish);
    }

    public Dish get(Integer id) {
        log.info("get dish {}", id);
        return crudDishRepository.findDishById(id);
    }

    public List<Dish> getAll() {
        log.info("empty getAll");
        return crudDishRepository.findAll();
    }

    public boolean delete(int id) {
        return crudDishRepository.deleteDishById(id);
    }

    @Transactional
    public void update(Dish dish) {
        log.info("update dish {}", dish.getId());
        crudDishRepository.save(dish);
    }

}
