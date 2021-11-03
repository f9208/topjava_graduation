package ru.f9208.choiserestaurant.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.f9208.choiserestaurant.model.entities.Dish;
import ru.f9208.choiserestaurant.utils.ValidatorUtil;

import java.time.LocalDate;
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

    @CacheEvict(value = {"getMenu"}, allEntries = true)
    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        Assert.notNull(dish, "Dish must not be null");
        log.info("save dish for restaurant {}", restaurantId);
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null)
            return null;
        dish.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudDishRepository.save(dish);
    }

    @CacheEvict(value = {"getMenu"}, allEntries = true)
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        log.info("create dish for restaurant {}", restaurantId);
        return save(dish, restaurantId);
    }

    @CacheEvict(value = {"getMenu"}, allEntries = true)
    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        log.info("update dish for restaurant {}", restaurantId);
        ValidatorUtil.checkNotFoundWithId(save(dish, restaurantId), restaurantId);
    }

    @CacheEvict(value = {"getMenu"}, allEntries = true)
    public boolean delete(int dishId, int restaurantId) {
        log.info("delete dish {} for restaurant {}", dishId, restaurantId);
        boolean result = crudDishRepository.delete(dishId, restaurantId) != 0;
        ValidatorUtil.checkNotFoundWithId(result, restaurantId);
        return result;
    }

    public Dish get(Integer dishId, int restaurantId) {
        log.info("get dish {} for restaurant {}", dishId, restaurantId);
        Dish resultDish = crudDishRepository.findById(dishId)
                .filter(dish -> dish.getRestaurant().getId() == restaurantId)
                .orElse(null);
        return ValidatorUtil.checkNotFoundWithId(resultDish, restaurantId);
    }

    @Cacheable(value = "getMenu", key = "#restaurantId")
    public List<Dish> getMenu(int restaurantId) {
        log.info("getMenu for restaurant {} ", restaurantId);
        return crudDishRepository.findAllByRestaurantIdAndDayOrderById(restaurantId, LocalDate.now());
    }

    @CacheEvict(value = "getMenu", allEntries = true)
    public void prepareMenuToUpdate(List<Dish> dishes, int restaurantId) {
        for (Dish dish : dishes) {
            Dish oldDish = crudDishRepository.getOne(dish.getId());
            if (dish.getName() == null || dish.getName().isBlank()) dish.setName(oldDish.getName());
            if (dish.getDay() == null) dish.setDay(oldDish.getDay());
            if (dish.getPrice() == null || dish.getPrice() == 0) dish.setPrice((oldDish.getPrice()));
            if (dish.getRestaurant() == null) dish.setRestaurant(oldDish.getRestaurant());
            save(dish, restaurantId);
        }
    }


}
