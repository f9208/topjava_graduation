package ru.f9208.choiserestaurant.model.entities;

import ru.f9208.choiserestaurant.model.HasId;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractNamedEntity implements HasId {
    public static final int START_SEQ = 10_000;

    @NotBlank(message = "введите название длиной не больше 100 символов")
    @Size(min = 1, max = 100, message = "")
    @Column(name = "name", nullable = false)
    String name;

    public AbstractNamedEntity() {
    }

    protected AbstractNamedEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + '(' + name + ')';
    }

}
