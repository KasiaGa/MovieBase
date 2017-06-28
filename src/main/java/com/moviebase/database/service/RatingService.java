package com.moviebase.database.service;

import com.moviebase.database.HibernateUtils;
import com.moviebase.database.model.Rating;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class RatingService {

    public static void save(Rating rating) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(rating);
        session.getTransaction().commit();
    }

    public static void update(Rating rating) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(rating);
        session.getTransaction().commit();
    }

    public static void delete(Rating rating) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(rating);
        session.getTransaction().commit();
    }

    public int getFilmRating(int movieID){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<Rating> ratings = session.createQuery("FROM Rating r WHERE r.movie.id = :movieID", Rating.class).setParameter("movieID", movieID).getResultList();
        float ratingSum = 0;
        int count = 0;
        for(Rating r: ratings){
            ratingSum += r.getScore();
            count ++;
        }
        session.getTransaction().commit();
        return count>0?Math.round(ratingSum/count):0;
    }

    public int getUserRating(int movieID, int userID){
        try {
            Session session = HibernateUtils.getSession();
            session.beginTransaction();
            Rating rating = session.createQuery("FROM Rating r WHERE r.movie.id = :movieID AND r.user.id = :userID", Rating.class).setParameter("movieID", movieID).setParameter("userID", userID).getSingleResult();
            return rating.getScore();
        } catch (NoResultException e){
            return -1;
        }
    }
}
