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
    private final CrudRestaurantRepository crudRestaurantRepository;

    public DishRepository(CrudDishRepository crudDishRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudDishRepository = crudDishRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        log.info("save dish for restaurant {}", restaurantId);
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null)
            return null;
//        https://www.javacodemonk.com/difference-between-getone-and-findbyid-in-spring-data-jpa-3a96c3ff
//        getOne - взять ссылку на обьект.6_01, 3:00
        dish.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudDishRepository.save(dish);
    }

    public int delete(int id, int restaurantId) {
        log.info("delete dish {} for restaurant {}", id, restaurantId);
        return crudDishRepository.delete(id, restaurantId);
    }

    public Dish get(Integer id, int restaurantId) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return crudDishRepository.findById(id)
                .filter(dish -> dish.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    public List<Dish> getMenu(int restaurantId) {
        log.info("getAllDish (menu) for restaurant {} ", restaurantId);
        return crudDishRepository.findAllByRestaurantId(restaurantId);
    }
}
