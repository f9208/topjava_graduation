package ru.f9208.choiserestaurant.web;

import ru.f9208.choiserestaurant.model.entities.Dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validation {
    public static Map<Integer, Boolean> validateMenu(List<Dish> menu) {
        Map<Integer, Boolean> errors = new HashMap<>();
        for (Dish dish : menu) {
            if (dish.getName().isBlank()
                    && (dish.getPrice() != null && dish.getPrice() >= 0)) {
                errors.put(dish.getId(), true);
            }
            if (!dish.getName().isBlank() && (dish.getPrice() == null)) {
                errors.put(dish.getId(), true);
            }
        }
        return errors;
    }
}
