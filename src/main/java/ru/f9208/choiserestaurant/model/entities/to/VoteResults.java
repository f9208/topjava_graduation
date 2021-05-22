package ru.f9208.choiserestaurant.model.entities.to;

import java.beans.ConstructorProperties;

public class VoteResults {
    int restaurantId;
    long voteCount;
    @ConstructorProperties({"restaurantId", "voteCount"})
    public VoteResults(int restaurantId, long voteCount) {
        this.restaurantId = restaurantId;
        this.voteCount = voteCount;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public long getVoteCount() {
        return voteCount;
    }
}