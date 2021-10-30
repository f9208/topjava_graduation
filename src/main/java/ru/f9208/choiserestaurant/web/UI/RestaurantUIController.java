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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.f9208.choiserestaurant.model.entities.Dish;
import ru.f9208.choiserestaurant.model.entities.Restaurant;
import ru.f9208.choiserestaurant.model.entities.User;
import ru.f9208.choiserestaurant.repository.DishRepository;
import ru.f9208.choiserestaurant.repository.RestaurantRepository;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("/restaurants/{id}")
    public String updateRestaurant(@ModelAttribute("restaurant") @Valid Restaurant restaurant,
                                   BindingResult bindingResult,
                                   @PathVariable Integer id,
                                   Model model, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("flashAttribute", bindingResult);
            return "restaurant";
        }
        restaurantRepository.update(restaurant);
        return "restaurant";
    }

    @GetMapping("/restaurants/{id}/edit")
    public String restEdit(@ModelAttribute("restaurant") Restaurant restaurant,
                           Model model,
                           @PathVariable int id,
                           @AuthenticationPrincipal User user) {
        Restaurant restaurant1 = restaurantRepository.getOne(id);
        restaurant1.setMenu(dishRepository.getMenu(id));
        model.addAttribute("restaurant", restaurant1);
        return "restaurantEdit";
    }

    @PostMapping("/restaurants/{id}/edit")
    public String restEditUpdate(@ModelAttribute("restaurants") @Valid Restaurant restaurant,
                                 BindingResult bindingResult,
                                 @PathVariable Integer id,
                                 Model model,
                                 @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return "restaurantEdit";
        }
        System.out.println(restaurant.getMenu());
        restaurantRepository.update(restaurant);
        return "redirect:/restaurants/" + id;
    }
}
