package ru.topjava.graduation.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ExtendWith(SpringExtension.class)
@Sql(scripts = {"classpath:db/initDB.sql","classpath:db/populateDB.sql" }, config = @SqlConfig(encoding = "UTF-8"))
class RestaurantRepositoryTest {
    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    void getAll() {
        System.out.println(restaurantRepository.getAll());
    }

    @Test
    void  getOne(){
        System.out.println(restaurantRepository.getOne(10010));
    }

//    @Test
//    void getWithDish() {
//        restaurantRepository.getWithDish(10010);
//    }
}