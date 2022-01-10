package ru.f9208.choicerestaurant;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.f9208.choicerestaurant.repository.DishRepository;

class GraduationApplicationTests {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx =
                     new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")) {
//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            DishRepository dishRepository = appCtx.getBean(DishRepository.class);
        }
    }
}
