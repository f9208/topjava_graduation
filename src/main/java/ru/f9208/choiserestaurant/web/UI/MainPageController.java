package ru.f9208.choiserestaurant.web.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.f9208.choiserestaurant.repository.RestaurantRepository;
import ru.f9208.choiserestaurant.utils.imageUtils.HandlerImage;

import java.time.LocalDate;

@Controller
public class MainPageController {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    HandlerImage handlerImage;

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("restaurants", restaurantRepository.getAllWithVotesBetween(LocalDate.now(), LocalDate.now()));
        return "index";
    }
}