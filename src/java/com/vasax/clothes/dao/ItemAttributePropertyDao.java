package com.vasax.clothes.dao;

import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.ItemAttributeProperty;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by vasax32 on 12.04.15.
 */
@Named
public class ItemAttributePropertyDao extends AbstractGenericDao<ItemAttributeProperty> {
    public ItemAttributeProperty getByItemIdAndAttributeId(int itemId, int attributeId) {
        Query query = entityManager.createQuery("select iap from ItemAttributeProperty iap where iap.item.id = :itemId and iap.attribute.id = :attributeId");
        query.setParameter("itemId", itemId);
        query.setParameter("attributeId", attributeId);
        try {
            return (ItemAttributeProperty) query.getSingleResult();

        } catch (NoResultException e){
            return null;
        }
    }

    public List<ItemAttributeProperty> getByItemId(int itemId) {
        Query query = entityManager.createQuery("select iap from ItemAttributeProperty iap where iap.item.id = :itemId");
        query.setParameter("itemId", itemId);
        return query.getResultList();
    }

    public void remove(ItemAttributeProperty selectedIap) {
        entityManager.remove(selectedIap);
    }

    public List<Item> selectActiveItemsWithFilter(int categoryId, int attributeId, int propertyId) {
        TypedQuery<Item> query = entityManager.createQuery("select iap.item from ItemAttributeProperty iap where" +
                " iap.item.active = true and (iap.item.category.id = :categoryId or iap.item.category.parent.id = :categoryId) and iap.attribute.id = :attributeId and iap.property.id = :propertyId", Item.class);
        query.setParameter("categoryId", categoryId);
        query.setParameter("attributeId", attributeId);
        query.setParameter("propertyId", propertyId);
        return query.getResultList();
    }

    public List<Integer> selectActiveItemIdsWithFilter(int categoryId, int attributeId, int propertyId, Double nativeMinPrice, Double nativeMaxPrice) {
        TypedQuery<Integer> query = entityManager.createQuery("select iap.item.id from ItemAttributeProperty iap where" +
                " iap.item.active = true and (iap.item.category.id = :categoryId or iap.item.category.parent.id = :categoryId)" +
                " and iap.attribute.id = :attributeId and iap.property.id = :propertyId" +
                " and iap.item.price >= :minPrice and iap.item.price <= :maxPrice", Integer.class);
        query.setParameter("categoryId", categoryId);
        query.setParameter("attributeId", attributeId);
        query.setParameter("propertyId", propertyId);
        query.setParameter("minPrice", nativeMinPrice);
        query.setParameter("maxPrice", nativeMaxPrice);
        return query.getResultList();
    }

    public List<ItemAttributeProperty> getByPropertyId(int propertyId) {
        TypedQuery<ItemAttributeProperty> query = entityManager.createQuery("select iap from ItemAttributeProperty iap where iap.property.id = :propId", ItemAttributeProperty.class);
        query.setParameter("propId", propertyId);
        return query.getResultList();
    }

    public List<ItemAttributeProperty> getByAttributeIdAndCategoryOrParentCategory(int attributeId, int categoryId) {
        Query query = entityManager.createQuery(
                "select iap from ItemAttributeProperty iap where iap.attribute.id = :attributeId and (iap.item.category.id = :categoryId or iap.item.category.parent.id = :categoryId)");
        query.setParameter("attributeId", attributeId);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }

    public List<ItemAttributeProperty> getByPropertyAndAttributeAndCategoryOrParentCategory(int attributeId, int propertyId, int categoryId) {
        Query query = entityManager.createQuery(
                "select iap from ItemAttributeProperty iap where iap.attribute.id = :attributeId and iap.property.id = :propertyId and" +
                        " (iap.item.category.id = :categoryId and iap.item.active = true )");
        query.setParameter("attributeId", attributeId);
        query.setParameter("propertyId", propertyId);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }
}
