package com.vasax.clothes.dao;

import com.vasax.clothes.entities.Article;
import com.vasax.clothes.entities.News;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Vasax on 11.05.2015.
 */
@Named
public class ArticleDao extends AbstractGenericDao<Article> {
    public List<Article> selectAllOrderedByLastModifiedDate(Boolean showNotActive) {
        if(!showNotActive)
            return selectAllActiveOrderedByLastModifiedDate();
        TypedQuery<Article> query = entityManager.createQuery("select article from Article article order by article.orderId desc", Article.class);
        return query.getResultList();
    }

    public List<Article> selectAllActiveOrderedByLastModifiedDate() {
        TypedQuery<Article> query = entityManager.createQuery("select article from Article article where article.active = true order by article.orderId desc", Article.class);
        return query.getResultList();
    }
}
