package ru.topjava.graduation.model.entities;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {
    @Column(name = "menu")

    @ManyToMany
    private Set<Dish> menu;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Set<Dish> getMenu() {
        return menu;
    }

    public void setMenu(Set<Dish> menu) {
        this.menu = menu;
    }
}
/*

    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @JoinColumn(name = "user_id") //https://stackoverflow.com/a/62848296/548473
    private Set<Role> roles;
*/