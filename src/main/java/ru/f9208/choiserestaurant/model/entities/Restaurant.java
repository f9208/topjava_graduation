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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private ImageLabel label;
    @Column(name = "description")
    private String description;
    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, ImageLabel label, String description) {
        super(name);
        this.id = id;
        this.label = label;
        this.description = description;
        this.enabled = true;
    }

    public Restaurant(Integer id, String name, ImageLabel label, String description, boolean enabled) {
        this(id, name, label, description);
        this.setEnabled(enabled);
    }

    public Restaurant(Restaurant copyRestaurant) {
        this(null, copyRestaurant.getName(), copyRestaurant.getLabel(),copyRestaurant.getDescription(), copyRestaurant.isEnabled());
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

    public ImageLabel getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public void setLabel(ImageLabel label) {
        this.label = label;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name +
                "\', enabled='" + enabled +
                "\', imageLabel{'" + label +
                "\'}, description='{" + description +
                "\'}";
    }
}