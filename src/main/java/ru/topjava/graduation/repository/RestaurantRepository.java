package ru.topjava.graduation.repository;

import org.springframework.stereotype.Repository;
import ru.topjava.graduation.model.entities.Restaurant;

import java.util.List;

@Repository
public class RestaurantRepository {
    CrudRestaurantRepository crudRestaurantRepository;

    public RestaurantRepository(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAll() {
        return crudRestaurantRepository.findAll();
    }

    public Restaurant get(int id) {
        return crudRestaurantRepository.findById(id).orElse(null);
    }

    public Restaurant getWithDish(Integer id) {
        return crudRestaurantRepository.getOneWithDish(id);
    }

    public void delete(int id) {
        crudRestaurantRepository.deleteById(id);
    }
}
