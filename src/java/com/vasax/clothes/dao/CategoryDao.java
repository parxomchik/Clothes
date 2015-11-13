package com.vasax.clothes.dao;

import com.vasax.clothes.entities.Category;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by oper4 on 07.11.2014.
 */
@Named
public class CategoryDao extends AbstractGenericDao<Category> {

    @Override
    public List<Category> selectAll() {
        Query query = entityManager.createQuery("SELECT a FROM Category a order by a.orderId");
        List<Category> entities = query.getResultList();
        return entities;
    }

    public List<Category> selectAllDetached() {
        Query query = entityManager.createQuery("SELECT a FROM Category a order by a.orderId");
        List<Category> entities = query.getResultList();
        for (Category entity : entities) {
            entityManager.detach(entity);
        }
        return entities;
    }

    public List<Category> selectActive() {
        Query query = entityManager.createQuery("SELECT a FROM Category a where a.active = true order by a.orderId");
        List<Category> entities = query.getResultList();
        return entities;
    }

    public List<Category> selectMainAndActive() {
        Query query = entityManager.createQuery("SELECT a FROM Category a where a.active = true and a.parent IS NULL order by a.orderId");
        List entities = query.getResultList();
        return entities;
    }

    public List<Category> selectSubActiveByMainId(int id) {
        Query query = entityManager.createQuery("SELECT a FROM Category a where a.active = true and a.parent.id =:id order by a.orderId");
        query.setParameter("id", id);
        List entities = query.getResultList();
        return entities;
    }

    public List<Category> selectAllSub() {
        Query query = entityManager.createQuery("SELECT a FROM Category a where a.active = true and a.parent IS NOT NULL order by a.orderId");
        List entities = query.getResultList();
        return entities;
    }

    public Category selectActiveById(int categoryId) {
        TypedQuery<Category> query = entityManager.createQuery("SELECT a FROM Category a where a.active = true and a.id = :categoryId", Category.class);
        query.setParameter("categoryId", categoryId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
