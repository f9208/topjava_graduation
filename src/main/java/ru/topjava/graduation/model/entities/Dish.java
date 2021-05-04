package ru.topjava.graduation.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "dishes",uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name"}, name = "dish_unique_restaurant_dishName_idx")})
public class Dish extends AbstractNamedEntity {
    @Id
    @SequenceGenerator(name = "dish_seq", sequenceName = "dish_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dish_seq")
    protected Integer id;

    //todo сделать прайс валидным прайсом
    @NumberFormat
    @NotNull
    private Integer price;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false) //название колонки в таблице
    @NotNull
    private Restaurant restaurant;

    @Column(name = "added", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled;

    public Dish() {
    }

    public Dish(Dish dish) {
        this(dish.getId(), dish.getName(), dish.getPrice(), dish.getRestaurant(), dish.getDateTime(), dish.isEnabled());
    }

    public Dish(Integer id, String name, Integer price, Restaurant restaurant, LocalDateTime dateTime, boolean enabled) {
        super(name);
        this.id = id;
        this.price = price;
        this.restaurant = restaurant;
        this.dateTime = dateTime;
        this.enabled = enabled;
    }

    public Dish(Integer id, String name, Integer price) {
        this(id, name, price, null, LocalDateTime.now(), true);
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

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", dateTime=" + dateTime +
                ", in menu=" + enabled +
                '}';
    }
}