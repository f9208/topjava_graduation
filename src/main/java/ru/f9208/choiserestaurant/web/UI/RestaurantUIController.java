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
import ru.f9208.choiserestaurant.repository.ImageLabelRepository;
import ru.f9208.choiserestaurant.repository.RestaurantRepository;
import ru.f9208.choiserestaurant.utils.imageUtils.HandlerImage;
import ru.f9208.choiserestaurant.web.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RestaurantUIController {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private HandlerImage handlerImage;
    @Autowired
    private ImageLabelRepository imageLabelRepository;

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
            errors = Validation.validateMenu(restaurant.getMenu());
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
                          @PathVariable Integer restId) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("restaurant", restaurantRepository.getOne(restId));
            return "restaurantEdit";
        }
        System.out.println("try to save dishes");
        dish.setDay(LocalDate.now());
        dishRepository.save(dish, restId);
        model.addAttribute("restaurant", restaurantRepository.getOne(restId));
        return "restaurantEdit";
    }
}
