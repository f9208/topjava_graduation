package ru.f9208.choicerestaurant.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.f9208.choicerestaurant.repository.RestaurantRepository;

import java.time.LocalDate;

@Controller
public class MainPageController {
    private final RestaurantRepository restaurantRepository;

    public MainPageController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("restaurants", restaurantRepository.getAllWithVotesBetween(LocalDate.now(), LocalDate.now()));
        return "index";
    }

    @GetMapping("/test")
    public String test(Model model) {
        return "test";
    }
}