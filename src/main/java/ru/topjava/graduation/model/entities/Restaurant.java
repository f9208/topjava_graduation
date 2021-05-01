package ru.topjava.graduation.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {
    @Id
    @SequenceGenerator(name = "restaurant_seq", sequenceName = "restaurant_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq")
    private Integer id;
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Dish> menu;

//    @OneToOne(mappedBy = "restaurant",fetch = FetchType.LAZY)
//    @JsonBackReference
//    private Vote vote;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(name);
    }

    public List<Dish> getMenu() {
        return menu;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}