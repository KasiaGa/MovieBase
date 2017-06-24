package com.moviebase.database.service;

import com.moviebase.database.HibernateUtils;
import com.moviebase.database.model.Movie;
import org.hibernate.Session;

import java.util.List;

public class MovieService {

    public static List<Movie> getAllMovies(){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<Movie> movies = session.createQuery("FROM Movie", Movie.class).getResultList();

        session.getTransaction().commit();
        return movies;
    }
}
