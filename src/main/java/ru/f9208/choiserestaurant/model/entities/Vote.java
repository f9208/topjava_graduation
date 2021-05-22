package ru.f9208.choiserestaurant.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ru.f9208.choiserestaurant.model.HasId;

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
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false) //название колонки в таблице
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(Integer id, LocalDate date, int userId, Restaurant restaurant) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.restaurant = restaurant;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setUser(int user) {
        this.userId = user;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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
                "id=" + id +
                ", date=" + date +
                ", user_id=" + userId +
                ", restaurant_id=" + restaurant.getId() +
                '}';
    }
}
