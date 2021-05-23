package ru.f9208.choicerestaurant.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static ru.f9208.choicerestaurant.repository.testData.RestaurantTestData.meatHome;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ExtendWith(SpringExtension.class)
@Sql(scripts = {"classpath:db/initDB.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
public abstract class AbstractStarterTest {
    @BeforeAll
    static void init() {
        meatHome.getName();
    }
}
