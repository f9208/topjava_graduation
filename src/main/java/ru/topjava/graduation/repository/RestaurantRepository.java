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

    List<Restaurant> getAll() {
        return crudRestaurantRepository.findAll();
    }

    Restaurant getOne(int id) {
        return crudRestaurantRepository.findById(id).orElse(null);
    }
//    Restaurant getWithDish(Integer id) {
//        Restaurant r=crudRestaurantRepository.getWithDish(10010);
//        System.out.println(r);
//        return r;
//    }
}
