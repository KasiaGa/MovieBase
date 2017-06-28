package com.moviebase.database.service;

import com.moviebase.database.HibernateUtils;
import com.moviebase.database.model.Like;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class LikeService {
    public static void save(Like like) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(like);
        session.getTransaction().commit();
    }

    public static void update(Like like) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(like);
        session.getTransaction().commit();
    }

    public static void delete(Like like) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(like);
        session.getTransaction().commit();
    }

    public static boolean isLikes(int movieID, int userID){
       try{
           Session session = HibernateUtils.getSession();
           session.beginTransaction();
           Like like = session.createQuery("FROM Like l WHERE l.movie.id = :movieID AND l.user.id = :userID", Like.class)
                   .setParameter("movieID", movieID).setParameter("userID",userID).getSingleResult();
           return true;
       }catch(NoResultException e){
           return false;
       }
    }

    public void deleteLike(int movieID, int userID) {
        try {
            Session session = HibernateUtils.getSession();
            session.beginTransaction();
            Like like = session.createQuery("FROM Like l WHERE l.movie.id = :movieID AND l.user.id = :userID", Like.class)
                    .setParameter("movieID", movieID).setParameter("userID", userID).getSingleResult();
            session.delete(like);
            session.getTransaction().commit();
        } catch (NoResultException e) {

        }
    }
}
