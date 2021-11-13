package ru.f9208.choiserestaurant.web.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.f9208.choiserestaurant.model.AuthorizedUser;
import ru.f9208.choiserestaurant.model.entities.*;
import ru.f9208.choiserestaurant.repository.DishRepository;
import ru.f9208.choiserestaurant.repository.RestaurantRepository;
import ru.f9208.choiserestaurant.repository.VoteRepository;
import ru.f9208.choiserestaurant.utils.imageUtils.HandlerImage;
import ru.f9208.choiserestaurant.web.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
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
    @Autowired
    private VoteRepository voteRepository;

    @GetMapping("/restaurants/{id}")
    public String getRestaurant(@PathVariable Integer id,
                                Model model,
                                @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Restaurant restaurant = restaurantRepository.getWithMenuAndVoteForToday(id);
        Vote vote = null;
        if (authorizedUser != null) {
            int userId = authorizedUser.getUserTo().getId();
            vote = voteRepository.getVoteByUserIdToday(userId);
        }
        model.addAttribute("vote", vote);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("dish", new Dish());
        return "restaurant";
    }

    @GetMapping("/restaurants/{id}/edit")
    public String editRestaurantGet(Model model,
                                    @PathVariable int id,
                                    @AuthenticationPrincipal UserDetails user) {
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
                                     @AuthenticationPrincipal UserDetails user,
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
            model.addAttribute("dish", new Dish());
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
                          HttpServletRequest httpServletRequest,
                          @AuthenticationPrincipal UserDetails user) {
        Restaurant restaurant = restaurantRepository.getWithMenu(restId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("restaurant", restaurant);
            return "restaurantEdit";
        }
        if (dishRepository.containDishByNameAndRestaurantId(dish.getName(), restId)) {
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("dish", new Dish());
            return "restaurantEdit";
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
            else {
                results.add(d);
            }
        }
        return results;
    }
}
