package com.vasax.clothes.dao;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by root on 07.10.14.
 */
@Repository
public abstract class AbstractGenericDao<T> implements Serializable {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;
    private Class<T> type;
//    @Inject
//    protected FullTextEntityManager fullTextEntityManager;
//    protected SearchFactory searchFactory;

    public AbstractGenericDao() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType)t;
        type = (Class)pt.getActualTypeArguments()[0];
    }

    @PostConstruct
    public void init(){
//        fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//        searchFactory = fullTextEntityManager.getSearchFactory();
    }

    public List<T> selectAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(type);
        Root<T> rootEntry = cq.from(type);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
//        Query query = entityManager.getCriteriaBuilder().createQuery(type).from(type).;
//        List<T> entities = query.getResultList();
//        return entities;
    }

    public T selectById(int id) {
        return (T)entityManager.find(type, id);
    }

    public T update(T entity) {
        T merge = entityManager.merge(entity);
        entityManager.flush();
        return merge;
    }

    public T insert(T entity) {
        entityManager.persist(entity);
        return entity;
    }
}
