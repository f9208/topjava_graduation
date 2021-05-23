package ru.f9208.choicerestaurant.model;

public interface HasId {
    Integer getId();

    void setId(Integer id);

    default boolean isNew() {
        return getId() == null;
    }
}
