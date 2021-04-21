package ru.topjava.graduation.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity {
    @NumberFormat
    @NotNull
    private Integer price;
 // todo сделать имя уникальным
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false) //название колонки в таблице
    @NotNull
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, String name, @NotNull Integer price) {
        super(id, name);
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dish {" +
                "id: " + id +
                ", name: '" + name + '\'' +
                ", price: " + price +
                '}';
    }
}