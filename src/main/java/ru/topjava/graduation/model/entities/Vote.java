package ru.topjava.graduation.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"})})
public class Vote extends AbstractBaseEntity {
    @Column(name = "date", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    @NotNull
    private LocalDate date;
    @NotNull
    @Column(name = "user_id")
    int userId;
    @NotNull
    @Column(name = "restaurant_id")
    int restaurantId;


    ///////////////////////
//    возмжно сделать трансфер обжект по факту учитывания голоса или нет. в базу сохранять все голоса подряд


    public int getRestaurantId() {
        return restaurantId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setUser(int user) {
        this.userId = user;
    }

    public void setRestaurantId(int restaurant) {
        this.restaurantId = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getUserId() {
        return userId;
    }

    public Vote() {
    }

    public Vote(Integer id, @NotNull LocalDate date, int userId, int restaurantId) {
        super(id);
        this.date = date;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "Id=" + id +
                ", date=" + date +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
