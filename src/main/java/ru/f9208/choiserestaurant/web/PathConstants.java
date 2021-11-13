package ru.f9208.choiserestaurant.web;

public class PathConstants {
    public static final String RESTAURANTID = "/{restaurant_id}";
    public static final String REST_RESTAURANTS = "/rest/restaurants";
    public static final String DISHES = "/dishes";
    public static final String RESTAURANTS_ID_DISHES = REST_RESTAURANTS + RESTAURANTID + DISHES;
    public static final String ADMIN_RESTAURANTS = "/rest/admin/restaurants";
    public static final String ADMIN_RESTAURANT_DISHES = ADMIN_RESTAURANTS + RESTAURANTID + DISHES;
    public static final String VOTE = "/vote";
}
