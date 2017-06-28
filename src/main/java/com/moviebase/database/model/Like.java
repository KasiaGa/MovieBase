package com.moviebase.database.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "like")
public class Like {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @JoinColumn(name = "movie_id")
    private Movie movie;

    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date")
    private Date date;

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
}
