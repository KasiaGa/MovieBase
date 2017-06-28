package com.moviebase.database.model;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    public User() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User(String name, String email, String imageUrl) {

        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public User(Map<String, Object> map){
        this.id = (int) map.get("id");
        this.name = (String) map.get("name");
        this.email = (String) map.get("email");
        this.imageUrl = (String) map.get("imageUrl");
    }
}
