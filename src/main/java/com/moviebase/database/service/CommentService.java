package com.moviebase.database.service;

import com.moviebase.database.HibernateUtils;
import com.moviebase.database.model.Comment;
import org.hibernate.Session;

import java.util.List;

public class CommentService {

    public static List<Comment> getCommentsByMovieId(int movieId) {
            Session session = HibernateUtils.getSession();
            session.beginTransaction();
            List<Comment> comments = session.createQuery("FROM Comment c WHERE c.movie.id = :movieId ORDER BY c.id DESC", Comment.class).setParameter("movieId", movieId).getResultList();
            return comments;
    }

    public static void save(Comment comment) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(comment);
        session.getTransaction().commit();
    }

    public static void update(Comment comment) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(comment);
        session.getTransaction().commit();
    }

    public static void delete(Comment comment) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(comment);
        session.getTransaction().commit();
    }

}
