package com.moviebase.database.service;

import com.moviebase.database.HibernateUtils;
import com.moviebase.database.model.Movie;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class MovieService {

    public static List<Movie> getAllMovies(){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<Movie> movies = session.createQuery("FROM Movie", Movie.class).getResultList();

        session.getTransaction().commit();
        return movies;
    }

    public static List<Movie> getAllLikedMovies(int userID){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<Movie> movies = session.createQuery("SELECT l.movie FROM Like l WHERE l.user.id = :userID", Movie.class).setParameter("userID",userID).getResultList();
        session.getTransaction().commit();
        return movies;
    }

    public static List<Movie> getMoviesWithTileContains(String title){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Movie> query = cb.createQuery(Movie.class);
        Root<Movie> m = query.from(Movie.class);
        query.select(m);
        query.where(cb.like(cb.lower(m.get("name")),"%"+title.toLowerCase()+"%"));

        List<Movie> movies = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return movies;
    }
}
