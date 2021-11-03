package ru.f9208.choiserestaurant.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.f9208.choiserestaurant.model.entities.Restaurant;
import ru.f9208.choiserestaurant.utils.ValidatorUtil;

import java.util.List;

@Repository
public class RestaurantRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());
    CrudRestaurantRepository crudRestaurantRepository;
    DishRepository dishRepository;

    public RestaurantRepository(CrudRestaurantRepository crudRestaurantRepository, DishRepository dishRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.dishRepository = dishRepository;
    }

    @CacheEvict(value = {"allRestaurants", "oneRestaurant"}, allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        log.info("create new restaurant");
        return save(restaurant);
    }

    @CacheEvict(value = {"allRestaurants", "oneRestaurant"}, allEntries = true)
    @Transactional
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        log.info("update restaurant with id {}", restaurant.getId());
        save(prepareRestaurantToUpdate(restaurant, crudRestaurantRepository.getOne(restaurant.getId())));
    }

    @CacheEvict(value = {"allRestaurants", "oneRestaurant"}, allEntries = true)
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    @CacheEvict(value = {"allRestaurants", "oneRestaurant"}, allEntries = true)
    public boolean delete(int id) {
        log.info("delete restaurant {}", id);
        boolean result = crudRestaurantRepository.delete(id) != 0;
        ValidatorUtil.checkNotFoundWithId(result, id);
        return result;
    }

    @Cacheable(value = "oneRestaurant", key = "#id")
    public Restaurant getOne(int id) {
        log.info("get restaurant {}", id);
        return ValidatorUtil.checkNotFoundWithId(crudRestaurantRepository.findById(id).orElse(null), id);
    }

    @Cacheable("allRestaurants")
    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return crudRestaurantRepository.findAll();
    }

    public List<Restaurant> getAllWithVotes() {
        log.info("get all restaurants with votes");
        return crudRestaurantRepository.getAllWithVote();
    }

    public Restaurant prepareRestaurantToUpdate(Restaurant newRestaurant, Restaurant oldRestaurant) {
        if (newRestaurant.getName() == null) newRestaurant.setName(oldRestaurant.getName());
        if (newRestaurant.getVote() == null) newRestaurant.setVote(oldRestaurant.getVote());
        if (newRestaurant.getLabel() == null) newRestaurant.setLabel((oldRestaurant.getLabel()));
        if (newRestaurant.getDescription() == null) newRestaurant.setDescription((oldRestaurant.getDescription()));
        if (newRestaurant.getMenu() != null) {
            dishRepository.prepareMenuToUpdate(newRestaurant.getMenu(), newRestaurant.getId());
        }
        return newRestaurant;
    }
}
