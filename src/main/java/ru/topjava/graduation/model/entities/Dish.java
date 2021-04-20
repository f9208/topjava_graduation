package ru.topjava.graduation.model.entities;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity {
    @NumberFormat
    @NotNull
    private Integer price;

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