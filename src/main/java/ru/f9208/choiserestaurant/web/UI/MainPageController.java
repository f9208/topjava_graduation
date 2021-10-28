package ru.f9208.choiserestaurant.web.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.f9208.choiserestaurant.repository.RestaurantRepository;

@Controller
public class MainPageController {
    @Autowired
    RestaurantRepository restaurantRepository;

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("restaurants", restaurantRepository.getAll());
        return "index";
    }
}
