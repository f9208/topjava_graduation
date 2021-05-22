package ru.f9208.choiserestaurant.model.entities.to;

import ru.f9208.choiserestaurant.model.entities.Vote;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class VoteTo {
    int voteId;
    LocalDate date;
    int userId;
    int restaurantId;

    @ConstructorProperties({"voteId", "date", "userId", "restaurantId"})
    public VoteTo(int voteId, LocalDate date, int userId, int restaurantId) {
        this.voteId = voteId;
        this.date = date;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public VoteTo(Vote vote) {
        this.voteId = vote.getId();
        this.date = vote.getDay();
        this.userId = vote.getUserId();
        this.restaurantId = vote.getRestaurant().getId();
    }

    public static List<VoteTo> convert(List<Vote> votes) {
        return votes.stream()
                .map(VoteTo::new)
                .collect(Collectors.toList());
    }

    public static List<VoteTo> convert(Vote... votes) {
        return convert(List.of(votes));
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getVoteId() {
        return voteId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getUserId() {
        return userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }
}
