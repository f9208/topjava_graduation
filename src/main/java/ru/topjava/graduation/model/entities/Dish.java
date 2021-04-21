package ru.topjava.graduation.model.entities;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity {
    @NumberFormat
    @NotNull
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id") //название колонки в таблице
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, String name, @NotNull Integer price) {
        super(id, name);
        this.price = price;
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