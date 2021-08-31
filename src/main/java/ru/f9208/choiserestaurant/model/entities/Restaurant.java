package ru.f9208.choiserestaurant.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurant_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {
    @Id
    @SequenceGenerator(name = "restaurant_seq", sequenceName = "restaurant_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq")
    private Integer id;
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Dish> menu;
    @JsonManagedReference
    @Nullable
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<Vote> vote;
    @Column(name = "label", nullable = false)
    private String label;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, String label) {
        super(name);
        this.id = id;
        this.label = label;
    }

    public List<Vote> getVote() {
        return vote;
    }

    public void setVote(List<Vote> votes) {
        this.vote = votes;
    }

    public List<Dish> getMenu() {
        return menu;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}