package ru.topjava.graduation.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.topjava.graduation.model.entities.Dish;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ExtendWith(SpringExtension.class)
@Sql(scripts = {"classpath:db/initDB.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
class DishRepositoryTest {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    DishRepository dishRepository;

    @Test
    void save() {
    }

    @Test
    void create() {
        Dish dish = new Dish(null, "panceke", 50);
        System.out.println(dishRepository.save(dish, 10010));
    }

    @Test
    void get() {
        Integer id = 10004;
        Integer restaurant_id = 10010;
        System.out.println(dishRepository.get(id, restaurant_id));
    }

    @Test
    void getAll() {
        Integer restaurant_id = 10020;
        System.out.println(dishRepository.getAllByRestaurantId(restaurant_id));
    }

    @Test
    void delete() {
        System.out.println( dishRepository.getAllByRestaurantId(10010));
        dishRepository.delete(10005,10010);
        System.out.println( dishRepository.getAllByRestaurantId(10010));
    }
}