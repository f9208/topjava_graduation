package ru.topjava.graduation;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.topjava.graduation.repository.DishRepository;

import java.util.Arrays;

class GraduationApplicationTests {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx =
                     new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")) {
//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            DishRepository dishRepository = appCtx.getBean(DishRepository.class);
            System.out.println(dishRepository.getAll());
        }
    }
}
