package com.vasax.clothes.dao;

import com.vasax.clothes.entities.News;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Vasax on 11.05.2015.
 */
@Named
public class NewsDao extends AbstractGenericDao<News> {
    public List<News> selectAllOrderedByLastModifiedDate(Boolean showNotActive) {
        if(!showNotActive)
            return selectAllActiveOrderedByLastModifiedDate();
        TypedQuery<News> query = entityManager.createQuery("select news from News news order by news.orderId desc", News.class);
        return query.getResultList();
    }

    public List<News> selectAllActiveOrderedByLastModifiedDate() {
        TypedQuery<News> query = entityManager.createQuery("select news from News news where news.active = true order by news.orderId desc", News.class);
        return query.getResultList();
    }
}
