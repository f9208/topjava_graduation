package ru.f9208.choiserestaurant.model.entities;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "image_labels")
//        , uniqueConstraints = {@UniqueConstraint(columnNames = "link_origin, link_reduced", name = "image_label_unique_link_idx")})
public class ImageLabel extends AbstractNamedEntity {
    @Id
    @SequenceGenerator(name = "image_seq", sequenceName = "image_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq")
    Integer id;
    @Column(name = "link_origin", unique = true)
    String linkOrigin;
    @NotNull
    @Column(name = "link_reduced", unique = true, nullable = false)
    String linkReduced;

    public ImageLabel() {
    }

    public ImageLabel(@Nullable String name, String linkOrigin, @NotNull String linkReduced) {
        this.name = name;
        this.linkOrigin = linkOrigin;
        this.linkReduced = linkReduced;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLinkOrigin(String linkOrigin) {
        this.linkOrigin = linkOrigin;
    }

    public void setLinkReduced(String linkReduced) {
        this.linkReduced = linkReduced;
    }

    public String getName() {
        return name;
    }

    public String getLinkOrigin() {
        return linkOrigin;
    }

    public String getLinkReduced() {
        return linkReduced;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
