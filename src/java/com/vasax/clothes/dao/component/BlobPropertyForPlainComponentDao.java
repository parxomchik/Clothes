package com.vasax.clothes.dao.component;

import com.vasax.clothes.dao.AbstractGenericDao;
import com.vasax.clothes.entities.component.BlobPropertyForPlainComponent;
import com.vasax.clothes.entities.component.PropertyForPlainComponent;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Created by Vasax on 04.05.2015.
 */
@Named
public class BlobPropertyForPlainComponentDao extends AbstractGenericDao<BlobPropertyForPlainComponent>{

    public BlobPropertyForPlainComponent selectByComponentIdAndKey(Integer componentId, String key) {
        TypedQuery<BlobPropertyForPlainComponent> query = entityManager.createQuery("select prop from BlobPropertyForPlainComponent prop" +
                " where prop.plainComponent.id = :componentId and prop.keyR = :key", BlobPropertyForPlainComponent.class);
        query.setParameter("componentId", componentId);
        query.setParameter("key", key);
        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
