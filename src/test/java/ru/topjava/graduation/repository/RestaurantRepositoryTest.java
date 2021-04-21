package ru.topjava.graduation.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.topjava.graduation.model.entities.Restaurant;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ExtendWith(SpringExtension.class)
@Sql(scripts = {"classpath:db/initDB.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
class RestaurantRepositoryTest {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    DishRepository dishRepository;

    @Test
    void getAll() {
        System.out.println(restaurantRepository.getAll());
    }

    @Test
    void getOne() {
        Restaurant r = restaurantRepository.get(10010);
    }

    @Test
    void getWithDish() {
        Restaurant r = restaurantRepository.getWithDish(10010);
        System.out.println(r.getMenu());
    }
}