package com.vasax.clothes.dao;

import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.RelativeItem;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by vasax32 on 18.04.15.
 */
@Named
public class RelativeItemDao extends AbstractGenericDao<RelativeItem> {

    public List<Integer> selectIdsByItem(int itemId) {
        TypedQuery<Integer> query = entityManager.createQuery("select ri.relative.id from RelativeItem ri where ri.item.id = :itemId", Integer.class);
        query.setParameter("itemId", itemId);
        return query.getResultList();
    }

    public List<Integer> selectActiveIdsByItem(int itemId, Boolean showNotActive) {
        if(showNotActive){
            return selectIdsByItem(itemId);
        }
        TypedQuery<Integer> query = entityManager.createQuery("select ri.relative.id from RelativeItem ri where ri.item.id = :itemId and ri.relative.active = true", Integer.class);
        query.setParameter("itemId", itemId);
        return query.getResultList();
    }

    public RelativeItem selectByItemIdAndRelativeId(int itemId, int relativeItemId) {
        TypedQuery<RelativeItem> query = entityManager.createQuery("select ri from RelativeItem ri where ri.item.id = :itemId and ri.relative.id = :relativeId", RelativeItem.class);
        query.setParameter("itemId", itemId);
        query.setParameter("relativeId", relativeItemId);
        return query.getSingleResult();
    }

    public void delete(RelativeItem relativeItem){
        entityManager.remove(relativeItem);
    }

    public List<Item> selectByItem(int itemId) {
        TypedQuery<Item> query = entityManager.createQuery("select ri.relative from RelativeItem ri where ri.item.id = :itemId", Item.class);
        query.setParameter("itemId", itemId);
        return query.getResultList();
    }
}
