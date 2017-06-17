package com.moviebase.database;

import com.moviebase.database.model.Movie;
import org.hibernate.Session;

public class Database {

    public static void main(String[] args) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Movie movie = new Movie();
        movie.setName("Movie1");
        movie.setDescription("Description");

        session.save(movie);
        session.getTransaction().commit();
        HibernateUtils.shutdown();
    }
}
