package ru.topjava.graduation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.topjava.graduation.model.entities.Dish;

import java.util.List;

import static ru.topjava.graduation.utils.ValidatorUtil.*;

@Repository
public class DishRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final CrudDishRepository crudDishRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public DishRepository(CrudDishRepository crudDishRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudDishRepository = crudDishRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @CacheEvict(value = {"allRestaurantsWithMenu","getOneWithMenu"}, allEntries = true)
    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        Assert.notNull(dish, "Dish must not be null");
        log.info("save dish for restaurant {}", restaurantId);
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null)
            return null;
        dish.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudDishRepository.save(dish);
    }

    @CacheEvict(value = {"allRestaurantsWithMenu","getOneWithMenu"}, allEntries = true)
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        checkNew(dish);
        log.info("create dish for restaurant {}", restaurantId);
        return save(dish, restaurantId);
    }

    @CacheEvict(value = {"allRestaurantsWithMenu","getOneWithMenu"}, allEntries = true)
    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        log.info("update dish for restaurant {}", restaurantId);
        checkNotFoundWithId(save(dish, restaurantId), restaurantId);
    }

    @CacheEvict(value = {"allRestaurantsWithMenu","getOneWithMenu"}, allEntries = true)
    public boolean delete(int dishId, int restaurantId) {
        log.info("delete dish {} for restaurant {}", dishId, restaurantId);
        boolean result = crudDishRepository.delete(dishId, restaurantId) != 0;
        checkNotFoundWithId(result, restaurantId);
        return result;
    }

    public Dish get(Integer dishId, int restaurantId) {
        log.info("get dish {} for restaurant {}", dishId, restaurantId);
        Dish resultDish = crudDishRepository.findById(dishId)
                .filter(dish -> dish.getRestaurant().getId() == restaurantId)
                .orElse(null);
        return checkNotFoundWithId(resultDish, restaurantId);
    }

    public List<Dish> getActualMenu(int restaurantId) {
        log.info("getActualMenu for restaurant {} ", restaurantId);
        return checkNotEmptyWithId(crudDishRepository.findAllByRestaurantIdAndEnabledIsTrue(restaurantId), restaurantId);
    }

    public List<Dish> getFullMenu(int restaurantId) {
        log.info("getFullMenu for restaurant {} ", restaurantId);
        return checkNotEmptyWithId(crudDishRepository.findAllByRestaurantId(restaurantId), restaurantId);
    }
    @CacheEvict(value = {"allRestaurantsWithMenu","getOneWithMenu"}, allEntries = true)
    @Transactional
    public void setVisibility(int dishId, int restaurantId, boolean state) {
        Dish d = get(dishId, restaurantId);
        checkNotFoundWithId(d, restaurantId);
        if (d.isEnabled() == state) {
            log.info("dish {} for restaurant {} is already {}", dishId, restaurantId, state ? "enable" : "disable");
            return;
        }
        log.info("dish {} for restaurant {} is {}", dishId, restaurantId, state ? "enable" : "disable");
        d.setEnabled(state);
    }
}
