package com.sda.dao;

import com.sda.db.HibernateUtils;
import com.sda.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UsersDAO {

    public void create(User user) {
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }
    }

    public boolean deleteByUsername(String username) {
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, username);
            boolean exist = user != null;
            if (exist) {
                session.remove(user);
            }
            transaction.commit();
            return exist;
        }
    }

    public List<User> findAll() {
        String query = "SELECT u FROM User u";
        try (Session session = HibernateUtils.openSession()) {
            return session.createQuery(query, User.class).list();
        }
    }

    public User findByUsername(String username) {
        try (Session session = HibernateUtils.openSession()) {
            return session.get(User.class, username);
        }
    }

    public User update(User updatedUser) {
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            User result = session.merge(updatedUser);
            transaction.commit();
            return result;
        }
    }

    public boolean existsByUsername(String username) {
        try (Session session = HibernateUtils.openSession()) {
            String query = "SELECT count(u) FROM User u Where u.username = :username";
            boolean result = session.createQuery(query, Long.class).setParameter("username", username)
                    .uniqueResult() > 0;
            return result;
        }
    }
}
