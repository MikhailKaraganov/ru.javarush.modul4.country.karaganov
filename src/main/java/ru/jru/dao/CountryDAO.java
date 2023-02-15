package ru.jru.dao;

import org.hibernate.SessionFactory;
import ru.jru.entity.Country;

public class CountryDAO extends EntityDAO<Country>{
    public CountryDAO(Class<Country> classToSet, SessionFactory sessionFactory) {
        super(classToSet, sessionFactory);
    }


}
