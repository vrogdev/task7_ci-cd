package com.epam.esm.model.entity;

import jakarta.persistence.*;

/**
 * This class represents tag entity.
 */
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    public Tag() {
    }

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
