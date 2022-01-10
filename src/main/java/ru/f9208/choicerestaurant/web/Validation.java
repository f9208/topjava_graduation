package ru.f9208.choicerestaurant.web;

import ru.f9208.choicerestaurant.model.entities.Dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validation {
    private Validation() {
    }

    public static Map<Integer, Boolean> validateMenu(List<Dish> menu) {
        Map<Integer, Boolean> errors = new HashMap<>();
        List<String> names = new ArrayList<>();
        for (Dish dish : menu) {
            if (dish.getName().isBlank()
                    && (dish.getPrice() != null && dish.getPrice() >= 0)) {
                errors.put(dish.getId(), true);
            }
            if (!dish.getName().isBlank() && (dish.getPrice() == null)) {
                errors.put(dish.getId(), true);
            }
            if (dish.getName() != null && names.contains(dish.getName())) {
                errors.put(dish.getId(), true);
            }
            names.add(dish.getName());
        }
        return errors;
    }
}
