package com.vasax.clothes.dao.component;

import com.vasax.clothes.dao.AbstractGenericDao;
import com.vasax.clothes.entities.component.PageComponent;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Created by Vasax on 04.05.2015.
 */
@Named
public class PageComponentDao extends AbstractGenericDao<PageComponent>{
    public PageComponent selectByIdentifier(String identifier){
        TypedQuery<PageComponent> query = entityManager.createQuery("select com from PageComponent com where com.identifier = :identifier", PageComponent.class);
        query.setParameter("identifier", identifier);
        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
