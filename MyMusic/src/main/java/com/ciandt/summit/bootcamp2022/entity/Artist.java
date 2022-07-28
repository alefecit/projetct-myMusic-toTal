package com.ciandt.summit.bootcamp2022.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Artistas")
public class Artist {

    @Id
    private final String id = UUID.randomUUID().toString();
    @Column(name = "nome")
    private String name;

    public Artist(){}

    public Artist(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}