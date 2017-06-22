package com.moviebase.database;

import com.moviebase.database.model.Movie;
import org.hibernate.Session;

import java.io.*;

public class Database {

    public static void main(String[] args) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Movie movie = new Movie();
        movie.setName("Title");
        movie.setDescription("Desc");
        movie.setImage(getByteaImage("custom path"));

        session.save(movie);
        session.getTransaction().commit();
        HibernateUtils.shutdown();
    }

    public static byte[] getByteaImage(String path){

        try {
        File file = new File(path);

        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];

            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
            return bos.toByteArray();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
