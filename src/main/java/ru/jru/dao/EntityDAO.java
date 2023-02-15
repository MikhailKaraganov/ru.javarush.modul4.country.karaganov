package ru.jru.dao;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityDAO<T> {
    private final Class<T> clazz;

    private SessionFactory sessionFactory;

    protected EntityDAO (final Class<T> classToSet, SessionFactory sessionFactory){
        this.clazz = classToSet;
        this.sessionFactory = sessionFactory;
    }

    public T getById(final int id) {
        return (T) getCurrentSession().get(clazz, id);
    }

    public List<T> getItems(int offset, int count) {
        Query query = getCurrentSession().createQuery("from " + clazz.getName(), clazz);
        query.setFirstResult(offset);
        query.setMaxResults(count);
        return query.getResultList();
    }

    public List<T> findAll() {
        List<T> resultList = new ArrayList<>();
        int step = 500;
        int totalCount = getAllAmount().intValue();
        Transaction transaction = getCurrentSession().beginTransaction();
        for (int i = 0; i < totalCount; i += step) {
            resultList.addAll(getItems(i, step));
        }
        transaction.commit();
        return resultList;
    }

    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }



    public Long getAllAmount(){
        Transaction transaction = getCurrentSession().beginTransaction();
        Long singleResult = (Long) getCurrentSession().createQuery("select count(*) from " + clazz.getName()).getSingleResult();
        transaction.commit();
        return singleResult;
    }
}
