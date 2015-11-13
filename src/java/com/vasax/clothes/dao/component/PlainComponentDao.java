package com.vasax.clothes.dao.component;

import com.vasax.clothes.dao.AbstractGenericDao;
import com.vasax.clothes.entities.component.PlainComponent;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Vasax on 04.05.2015.
 */
@Named
public class PlainComponentDao extends AbstractGenericDao<PlainComponent>{
    public PlainComponent selectByIdentifierAndPageComponent(String identifier, int idPageComponent){
        TypedQuery<PlainComponent> query = entityManager.createQuery("select com from PlainComponent com where" +
                " com.identifier = :identifier and com.pageComponent.id = :idPageComponent", PlainComponent.class);
        query.setParameter("identifier", identifier);
        query.setParameter("idPageComponent", idPageComponent);
        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    public List<PlainComponent> selectAllByPageComponent(int idPageComponent) {
        TypedQuery<PlainComponent> query = entityManager.createQuery("select pcom from PlainComponent pcom where pcom.pageComponent.id = :id", PlainComponent.class);
        query.setParameter("id", idPageComponent);
        return query.getResultList();
    }
}
