package ru.jru.connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.jru.dao.CityDAO;
import ru.jru.entity.City;
import ru.jru.entity.Country;
import ru.jru.entity.CountryLanguage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class SessionProvider {
    private final SessionFactory sessionFactory;
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session getCurrentSession(){
        return getSessionFactory().getCurrentSession();
    }

    public SessionProvider() {
        Properties properties = new Properties();

        try(InputStream in = new FileInputStream("src/main/resources/database.properties")) {
            properties.load(in);
        }
        catch (IOException e){
            e.printStackTrace();
        }

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

