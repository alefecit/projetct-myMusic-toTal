package com.ciandt.summit.bootcamp2022.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Musicas")
public class Music {

    @Id
    private String id = UUID.randomUUID().toString();
    @Column(name = "nome")
    private String name;
    @OneToOne
    @JoinColumn(name = "ArtistaId")
    private Artist artist;

    public Music() {
    }

    public Music(String name, Artist artist) {
        this.name = name;
        this.artist = artist;
    }

    public void setId(String id) {
        this.id = id;
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

    public Artist getArtista() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
