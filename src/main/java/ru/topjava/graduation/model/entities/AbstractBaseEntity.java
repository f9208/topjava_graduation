package ru.topjava.graduation.model.entities;

import ru.topjava.graduation.model.HasId;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractBaseEntity implements HasId {
    public static final int START_SEQ = 10_000;
    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    protected Integer id;

    public AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    public AbstractBaseEntity() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }

}
