package ru.f9208.choiserestaurant.web.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.f9208.choiserestaurant.model.entities.Dish;
import ru.f9208.choiserestaurant.model.entities.Restaurant;
import ru.f9208.choiserestaurant.model.entities.User;
import ru.f9208.choiserestaurant.repository.DishRepository;
import ru.f9208.choiserestaurant.repository.RestaurantRepository;
import ru.f9208.choiserestaurant.web.Validation;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RestaurantUIController {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    private DishRepository dishRepository;

    @GetMapping("/restaurants/{id}")
    public String getRestaurant(@PathVariable Integer id, Model model) {
        Restaurant restaurant = restaurantRepository.getOne(id);
        List<Dish> menu = dishRepository.getMenu(id);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("dishes", menu);
        return "restaurant";
    }

    @GetMapping("/restaurants/{id}/edit")
    public String editRestaurantGet(Model model,
                                    @PathVariable int id,
                                    @AuthenticationPrincipal User user) {
        Restaurant restaurant = restaurantRepository.getOne(id);
        restaurant.setMenu(dishRepository.getMenu(id));
        model.addAttribute("restaurant", restaurant);
        return "restaurantEdit";
    }

    @PostMapping("/restaurants/{id}/edit")
    public String editRestaurantPost(@ModelAttribute("restaurant") @Valid Restaurant restaurant,
                                     BindingResult bindingResult,
                                     @PathVariable Integer id,
                                     Model model,
                                     @AuthenticationPrincipal User user) {
        Map<Integer, Boolean> errors = Validation.validateMenu(restaurant.getMenu());
        if (bindingResult.hasErrors() || !errors.isEmpty()) {
            model.addAttribute("errors", errors);
            if (restaurant.getName().isBlank()) restaurant.setName(restaurantRepository.getOne(id).getName());
            return "restaurantEdit";
        }
        System.out.println("model: " + model);
        System.out.println(restaurant);
        restaurantRepository.update(restaurant);
        return "redirect:/restaurants/" + id;
    }
}
