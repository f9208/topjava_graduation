package ru.f9208.choiserestaurant.web.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.f9208.choiserestaurant.model.entities.Dish;
import ru.f9208.choiserestaurant.model.entities.Restaurant;
import ru.f9208.choiserestaurant.model.entities.User;
import ru.f9208.choiserestaurant.repository.DishRepository;
import ru.f9208.choiserestaurant.repository.RestaurantRepository;
import ru.f9208.choiserestaurant.web.Validation;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        model.addAttribute("dish", new Dish());
        return "restaurantEdit";
    }

    @PostMapping("/restaurants/{id}/edit")
    public String editRestaurantPost(@ModelAttribute("restaurant") @Valid Restaurant restaurant,
                                     BindingResult bindingResult,
                                     @PathVariable Integer id,
                                     Model model,
                                     @RequestParam("file") MultipartFile file,
                                     @AuthenticationPrincipal User user) {
        Map<Integer, Boolean> errors = new HashMap<>();
        try {
            fileHandle(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (restaurant.getMenu() != null) {
            errors = Validation.validateMenu(restaurant.getMenu());
        }
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

    @PostMapping("/restaurants/{restId}/add")
    public String addDish(@ModelAttribute("dish") @Valid Dish dish,
                          BindingResult bindingResult,
                          Model model,
                          @PathVariable Integer restId) {
        if (bindingResult.hasErrors()) {
            System.out.println("has errors");
            System.out.println(bindingResult);
            model.addAttribute("restaurant", restaurantRepository.getOne(restId));
            return "restaurantEdit";
        }
        System.out.println("try to save dishes");
        dish.setDay(LocalDate.now());
        dishRepository.save(dish, restId);
        model.addAttribute("restaurant", restaurantRepository.getOne(restId));
        return "restaurantEdit";
    }

    private void fileHandle(MultipartFile multipartFile) throws IOException {
        System.out.println("fileName:" + multipartFile.getOriginalFilename());
        System.out.println("fileSize:" + multipartFile.getSize());
        if (!multipartFile.isEmpty()) {

            File imageDir = new File("image");
            if (!imageDir.exists()) {
                imageDir.mkdir();
            }
            //todo зафиксить опционал на рендом или типа того
            File image = new File(imageDir, Optional.of(multipartFile.getOriginalFilename()).orElse("234"));
            image.createNewFile();


        } else {
            System.out.println("чет файл пустой");
        }
    }
}
