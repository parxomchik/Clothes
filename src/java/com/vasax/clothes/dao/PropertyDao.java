package com.vasax.clothes.dao;

import com.vasax.clothes.entities.Property;

import javax.inject.Named;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by vasax32 on 10.04.15.
 */
@Named
public class PropertyDao extends AbstractGenericDao<Property>{
    public List<Property> selectByAttribute(int id){
        Query query = entityManager.createQuery("select prop from Property prop where prop.attribute.id = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    public void deleteByObj(Property property) {
        entityManager.remove(property);
    }
}
