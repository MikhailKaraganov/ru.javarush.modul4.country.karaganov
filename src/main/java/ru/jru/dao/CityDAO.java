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

    @Override
    public List<City> findAll() {
        List<City> resultList = new ArrayList<>();
        int step = 500;
        int totalCount = getAllAmount().intValue();
        Transaction transaction = getCurrentSession().beginTransaction();
        for (int i = 0; i < totalCount; i += step) {
            Query query = getCurrentSession()
                    .createQuery("select c from City c join fetch c.countryId");
            query.setFirstResult(i);
            query.setMaxResults(i+step);
            resultList.addAll(query.list());
        }
        transaction.commit();
        return resultList;
    }
}
