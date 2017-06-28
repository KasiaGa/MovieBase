package com.moviebase.database.model;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private byte[] image;

    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String decription) {
        this.description = decription;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Movie(Map<String, Object> map){
        this.id = (int) map.get("id");
        this.name = (String) map.get("name") ;
        this.description = (String) map.get("description");
        this.image = ((String) map.get("image")).getBytes() ;
    }
}
