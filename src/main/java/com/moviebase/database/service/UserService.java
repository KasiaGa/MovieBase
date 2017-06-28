package com.moviebase.database.service;

import com.moviebase.database.HibernateUtils;
import com.moviebase.database.model.User;
import org.hibernate.Session;

import java.util.List;

public class UserService {

    public static List<User> getAllUser(){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<User> users = session.createQuery("FROM User", User.class).getResultList();

        session.getTransaction().commit();
        return users;
    }

   public static User getUser(int id){
       Session session = HibernateUtils.getSession();
       session.beginTransaction();
       User user = session.createQuery("FROM User u WHERE u.id = :id", User.class).setParameter("id",id).getSingleResult();
       return user;
   }
}
