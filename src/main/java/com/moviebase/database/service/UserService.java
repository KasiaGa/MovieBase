package com.moviebase.database.service;

import com.moviebase.database.HibernateUtils;
import com.moviebase.database.model.User;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class UserService {

    public static List<User> getAllUser() {
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<User> users = session.createQuery("FROM User", User.class).getResultList();

        session.getTransaction().commit();
        return users;
    }

    public static User getUser(int id) {
        try {
            Session session = HibernateUtils.getSession();
            session.beginTransaction();
            User user = session.createQuery("FROM User u WHERE u.id = :id", User.class).setParameter("id", id).getSingleResult();
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

    public static User getUserByEmail(String email) {
        try {
            Session session = HibernateUtils.getSession();
            session.beginTransaction();
            User user = session.createQuery("FROM User u WHERE u.email = :email", User.class).setParameter("email", email).getSingleResult();
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

    public static int save(User user) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        int id = (int) session.save(user);
        session.getTransaction().commit();
        return id;
    }

    public static void update(User user) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
    }

    public static void delete(User user) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
    }
}
