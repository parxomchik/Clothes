package com.vasax.clothes.dao;

import com.vasax.clothes.entities.Attribute;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by vasax32 on 09.04.15.
 */
@Named
public class AttributeDao extends AbstractGenericDao<Attribute>{
    public List<Attribute> getActiveByCategoryId(int id) {
        Query query = entityManager.createQuery("select distinct attr from Attribute attr where attr.category.id = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Attribute> getByCategoryId(int id) {
        Query query = entityManager.createQuery("select distinct attr from Attribute attr where attr.category.id = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Transactional
    public void deleteByObj(Attribute attribute) {
        entityManager.remove(attribute);
    }
}
