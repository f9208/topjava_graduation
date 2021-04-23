package ru.topjava.graduation.model.entities;

import ru.topjava.graduation.model.entities.to.VoteTo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"})})
public class Vote extends AbstractBaseEntity {
    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime date;
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    Restaurant restaurant;

    int getRestaurantId() {
        return restaurant.getId();
    }

    public void setDate(LocalDateTime dateTime) {
        this.date = dateTime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Vote() {
    }

    public Vote(VoteTo voteTo, User user) {
        this.user = user;
        this.date = voteTo.getLocalDateTime();
//        this.restaurant = voteTo.getRestaurant();
    }

    public Vote(Integer id, @NotNull LocalDateTime date, User user, Restaurant restaurant) {
        super(id);
        this.date = date;
        this.user = user;
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "Id=" + id +
                ", dateTime=" + date +
                ", userId=" + user.getId() +
                ", restaurantId=" + restaurant.getId() +
                '}';
    }
}
