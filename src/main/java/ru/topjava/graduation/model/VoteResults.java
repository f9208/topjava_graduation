package ru.topjava.graduation.model;

public class VoteResults {
    int restaurantId;
    long voteCount;

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
