package com.vasax.clothes.dao;

import com.vasax.clothes.entities.GlobalSetting;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Created by Vasax on 03.05.2015.
 */
@Named
public class GlobalSettingDao extends AbstractGenericDao<GlobalSetting>{
    public String selectByKey(String key){
        TypedQuery<String> query = entityManager.createQuery("select gs.valueR from GlobalSetting gs where gs.keyR = :keyR", String.class);
        query.setParameter("keyR", key);
        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
