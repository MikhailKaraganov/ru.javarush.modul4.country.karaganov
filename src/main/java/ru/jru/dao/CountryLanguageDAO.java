package ru.jru.dao;

import org.hibernate.SessionFactory;
import ru.jru.entity.CountryLanguage;

public class CountryLanguageDAO extends EntityDAO<CountryLanguage>{
    public CountryLanguageDAO(Class<CountryLanguage> classToSet, SessionFactory sessionFactory) {
        super(classToSet, sessionFactory);
    }
}
