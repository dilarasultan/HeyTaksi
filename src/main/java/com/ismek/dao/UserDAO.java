package com.ismek.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ismek.entity.User;
import com.ismek.util.HibernateUtil;
import org.hibernate.annotations.SourceType;

public class UserDAO {
    Session session = HibernateUtil.getSessionFactory().openSession();

    public boolean save(User user) {
        boolean result = true;
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            session.close();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        return session.createCriteria(User.class).list();
    }

    public User findUserbyId(long userId) {
        return (User) session.createQuery("from User where id=:id").
                setParameter("id", userId).uniqueResult();
    }

    public void deleteUser(long userId) {
        try {
            session.getTransaction().begin();
            session.createQuery("delete from User where id =:id").setParameter("id", userId).
                    executeUpdate();
            session.getTransaction().commit();
            System.out.println("Deleted the User Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed on Deleting the User");
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return;
    }

    public void updateUser(User user) {
        try {
            session.getTransaction().begin();
            session.update(user);
            session.getTransaction().commit();
            System.out.println("Updated the User Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed on Updating the User");
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return;
    }

    public boolean login(String username, String password) {
        User user = (User) session.createQuery("from User where name=:name and password=:password").
                setParameter("name", username).setParameter("password", password).uniqueResult();
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }
}
