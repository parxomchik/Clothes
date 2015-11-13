package com.vasax.clothes.dao;

import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.ItemImage;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by vasax32 on 12.04.15.
 */
@Named
public class ItemImageDao extends AbstractGenericDao<ItemImage>{

    public List<ItemImage> selectByItemId(int itemId){
        TypedQuery<ItemImage> query = entityManager.createQuery("select img from ItemImage img where img.item.id = :itemId order by img.orderId desc", ItemImage.class);
        query.setParameter("itemId", itemId);
        return query.getResultList();
    }

    public void remove(ItemImage itemImage) {
        entityManager.remove(itemImage);
    }

    public long selectCountOfImagesByItemId(int id) {
        TypedQuery<Long> query = entityManager.createQuery("select count(img) from ItemImage img where img.item.id = :itemId", Long.class);
        query.setParameter("itemId", id);
        return query.getSingleResult();
    }

    public List<Integer> selectIdsByItemId(int itemId) {
        TypedQuery<Integer> query = entityManager.createQuery("select img.id from ItemImage img where img.item.id = :itemId order by img.orderId desc", Integer.class);
        query.setParameter("itemId", itemId);
        return query.getResultList();
    }

    public List<String> selectColorsWithNotNullIdByIteId(int itemId) {
        TypedQuery<String> query = entityManager.createQuery("select img.color from ItemImage img" +
                " where img.item.id = :itemId and img.color is not NULL and img.color <>'' and img.colorId <> 0 order by img.orderId desc", String.class);
        query.setParameter("itemId", itemId);
        return query.getResultList();
    }

    public List<String> selectAllColors() {
        TypedQuery<String> query = entityManager.createQuery("select distinct img.color from ItemImage img" +
                " where img.color is not NULL and img.color <>''", String.class);
        return query.getResultList();
    }

    public List<Item> selectItemsByCategoryAndColor(int categoryId, String color) {
        TypedQuery<Item> query = entityManager.createQuery(
                "select img.item from ItemImage img where " +
                        "(img.item.category.id = :categoryId or img.item.category.parent.id = :categoryId)" +
                        " and img.color = :color", Item.class);
        query.setParameter("categoryId", categoryId);
        query.setParameter("color", color);
        return query.getResultList();
    }

    public List<Integer> selectActiveItemIdsByCategoryAndColor(int categoryId, String color, Double nativeMinPrice, Double nativeMaxPrice) {
        TypedQuery<Integer> query = entityManager.createQuery(
                "select distinct img.item.id from ItemImage img where " +
                        "(img.item.category.id = :categoryId or img.item.category.parent.id = :categoryId)" +
                        " and img.color = :color and img.item.price >= :minPrice and img.item.price <= :maxPrice and img.item.active = true", Integer.class);
        query.setParameter("categoryId", categoryId);
        query.setParameter("color", color);
        query.setParameter("minPrice", nativeMinPrice);
        query.setParameter("maxPrice", nativeMaxPrice);
        return query.getResultList();
    }

    public int selectColorIdByIteIdAndColor(int itemId, String color) {
        TypedQuery<Integer> query = entityManager.createQuery("select img.colorId from ItemImage img" +
                " where img.item.id = :itemId and img.color = :color", Integer.class);
        query.setParameter("itemId", itemId);
        query.setParameter("color", color);
        return query.getSingleResult();
    }

    public List<String> selectAllActiveColorsByCategoryId(int categoryId) {
        TypedQuery<String> query = entityManager.createQuery("select distinct img.color from ItemImage img" +
                " where img.color is not NULL and img.color <>'' and img.item.active = true " +
                "and (img.item.category.id = :categoryId or img.item.category.parent.id = :categoryId)", String.class);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }

    public List<Integer> AllIdsWhereWatermarkIsNull() {
        TypedQuery<Integer> query = entityManager.createQuery("select img.id from ItemImage img where img.needToBeUpdated = true", Integer.class);
        return query.getResultList();
    }
}
