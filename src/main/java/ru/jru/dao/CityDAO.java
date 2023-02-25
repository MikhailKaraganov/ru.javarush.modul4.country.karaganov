package ru.jru.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.jru.entity.City;
import ru.jru.entity.Country;

import java.util.ArrayList;
import java.util.List;

public class CityDAO extends EntityDAO<City>{

    public CityDAO(Class<City> classToSet, SessionFactory sessionFactory) {
        super(classToSet, sessionFactory);
    }

}