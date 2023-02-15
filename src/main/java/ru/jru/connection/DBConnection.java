package ru.jru.connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import ru.jru.dao.CityDAO;
import ru.jru.entity.City;
import ru.jru.entity.Country;
import ru.jru.entity.CountryLanguage;

import java.util.List;
import java.util.Properties;
import java.util.Set;

public class DBConnection {
    private final SessionFactory sessionFactory;
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session getCurrentSession(){
        return getSessionFactory().getCurrentSession();
    }

    public DBConnection() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/world");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");
        properties.put(Environment.STATEMENT_BATCH_SIZE, "100");

        sessionFactory = new Configuration()
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(CountryLanguage.class)
                .setProperties(properties)
                .buildSessionFactory();
    }

    public void testMysqlData(List<Integer> ids, CityDAO cityDAO) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            for (Integer id : ids) {
                City city = cityDAO.getById(id);
                Set<CountryLanguage> languages = city.getCountryId().getLanguages();
            }
            session.getTransaction().commit();
        }
    }
}

