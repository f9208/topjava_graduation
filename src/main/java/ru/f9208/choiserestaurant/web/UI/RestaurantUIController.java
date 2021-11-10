package ru.f9208.choiserestaurant.web.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.f9208.choiserestaurant.model.entities.Dish;
import ru.f9208.choiserestaurant.model.entities.ImageLabel;
import ru.f9208.choiserestaurant.model.entities.Restaurant;
import ru.f9208.choiserestaurant.model.entities.User;
import ru.f9208.choiserestaurant.repository.DishRepository;
import ru.f9208.choiserestaurant.repository.RestaurantRepository;
import ru.f9208.choiserestaurant.utils.imageUtils.HandlerImage;
import ru.f9208.choiserestaurant.web.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Controller
public class RestaurantUIController {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private HandlerImage handlerImage;

    @GetMapping("/restaurants/{id}")
    public String getRestaurant(@PathVariable Integer id, Model model) {
        Restaurant restaurant = restaurantRepository.getWithMenu(id);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("dish", new Dish());
        return "restaurant";
    }

    @GetMapping("/restaurants/{id}/edit")
    public String editRestaurantGet(Model model,
                                    @PathVariable int id,
                                    @AuthenticationPrincipal User user) {
        Restaurant restaurant = restaurantRepository.getWithMenu(id);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("dish", new Dish());
        return "restaurantEdit";
    }

    @PostMapping("/restaurants/{id}/edit")
    public String editRestaurantPost(@ModelAttribute("restaurant") @Valid Restaurant restaurant,
                                     BindingResult bindingResult,
                                     @PathVariable Integer id,
                                     Model model,
                                     @RequestParam("inputFile") MultipartFile inputFile,
                                     @AuthenticationPrincipal User user,
                                     HttpServletRequest request) throws Exception {
        ImageLabel imageLabel = null;
        if (!inputFile.isEmpty()) {
            imageLabel = handlerImage.serviceSaveInputFileImage(inputFile,
                    request.getServletContext().getRealPath(""),
                    inputFile.getOriginalFilename());
        }
        if (imageLabel != null) restaurant.setLabel(imageLabel);

        Map<Integer, Boolean> errors = new HashMap<>();
        if (restaurant.getMenu() != null) {
            List<Dish> menu = restaurant.getMenu();
            menu = deleteEmpty(menu, restaurant.getId());
            errors = Validation.validateMenu(menu);
            restaurant.setMenu(menu);
        }

        if (bindingResult.hasErrors() || !errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "restaurantEdit";
        }
        restaurantRepository.update(restaurant);
        return "redirect:/restaurants/" + id;
    }

    @PostMapping("/restaurants/{restId}/add")
    public String addDish(@ModelAttribute("dish") @Valid Dish dish,
                          BindingResult bindingResult,
                          Model model,
                          @PathVariable Integer restId,
                          HttpServletRequest httpServletRequest) {
        Restaurant restaurant = restaurantRepository.getWithMenu(restId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("restaurant", restaurant);
            return "restaurant";
        }
        dish.setDay(LocalDate.now());
        Dish saved = dishRepository.save(dish, restId);
        restaurant.getMenu().add(saved);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("dish", new Dish());
        String parentUrl = httpServletRequest.getParameter("parentUrl");
        return "redirect:" + parentUrl;
    }

    @GetMapping("/restaurants/{restId}/add")
    public String addDishGet(@PathVariable(name = "restId") Integer id) {
        return "redirect:/restaurants/" + id;
    }

    private List<Dish> deleteEmpty(List<Dish> dishes, int restId) {
        List<Dish> results = new ArrayList<>();
        for (Dish d : dishes) {
            if (d.getPrice() == null && d.getName().isBlank()) dishRepository.delete(d.getId(), restId);
            if (d.getPrice() != null && !d.getName().isBlank()) {
                results.add(d);
            }
        }
        return results;
    }
}
