package ru.topjava.graduation.model.entities;

import ru.topjava.graduation.model.HasId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"})})
public class Vote implements HasId {
    @Id
    @SequenceGenerator(name = "vote_seq", sequenceName = "vote_seq", allocationSize = 1, initialValue = AbstractNamedEntity.START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vote_seq")
    protected Integer id;

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
    public Vote() {
    }

    public Vote(Integer id, LocalDate date, int userId, int restaurantId) {
        this.id=id;
        this.date = date;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

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

    @Override
    public Integer getId() {
        return id;
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
