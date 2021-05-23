package ru.f9208.choiserestaurant.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name", "day"}, name = "dish_unique_restaurant_dishName_idx")})
public class Dish extends AbstractNamedEntity {
    @Id
    @SequenceGenerator(name = "dish_seq", sequenceName = "dish_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dish_seq")
    protected Integer id;

    @NumberFormat
    @NotNull
    private Integer price;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "day", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDate day;

    public Dish() {
    }

    public Dish(Dish dish) {
        this(dish.getId(), dish.getName(), dish.getPrice(), dish.getRestaurant(), dish.getDay());
    }

    public Dish(Integer id, String name, Integer price, Restaurant restaurant, LocalDate day) {
        super(name);
        this.id = id;
        this.price = price;
        this.restaurant = restaurant;
        this.day = day;
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

    public void setDay(LocalDate dateTime) {
        this.day = dateTime;
    }


    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
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
                ", day =" + day +
                '}';
    }
}