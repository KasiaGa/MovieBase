package com.moviebase.database.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date")
    private Date date;

    @Column(name = "content")
    private String content;

    public Comment() {

    }

    public Comment(Movie movie, User user, Date date, String content) {
        this.movie = movie;
        this.user = user;
        this.date = date;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment(Map<String, Object> map){
        this.id = (int) map.get("id");
        this.movie = new Movie((Map<String, Object>) map.get("movie"));
        this.user = new User((Map<String, Object>) map.get("user"));
        this.content = (String) map.get("content");
        this.date = (Date) map.get("date");
    }
}
