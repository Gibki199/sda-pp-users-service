package com.sda.db;

import com.sda.model.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

@Slf4j
public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static Session openSession() {
        return getSessionFactory().openSession();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            createSessionFactory();
        }
        return sessionFactory;
    }

    private static void createSessionFactory() {
        Configuration configuration = new Configuration();
               configuration.addAnnotatedClass(User.class);

        final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        try {
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            log.error("Creating SessionFactory failed! Exception: {}\n{}", e.getMessage(), e);
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }

}