package com.vasax.clothes.dao;

import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.enums.SortType;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by oper4 on 07.11.2014.
 */
@Named
public class ItemDao extends AbstractGenericDao<Item> {

    public List<Item> selectActive(int categoryId){
        Query query = entityManager.createQuery("SELECT dish from Item dish where dish.active = true and" +
                " (dish.category.id = :catId or dish.category.parent.id = :catId ) ");
        query.setParameter("catId", categoryId);
        return query.getResultList();
    }

    public List<Integer> selectActiveIdsOrderBySortType(int categoryId, Double nativeMinPrice, Double nativeMaxPrice){
        TypedQuery<Integer> query = entityManager.createQuery("SELECT item.id from Item item where item.active = true and" +
                " (item.category.id = :catId or item.category.parent.id = :catId ) " +
                " and item.price >= :minPrice and item.price <= :maxPrice", Integer.class);
        query.setParameter("minPrice", nativeMinPrice);
        query.setParameter("maxPrice", nativeMaxPrice);
        query.setParameter("catId", categoryId);
        return query.getResultList();
    }

    private String getOrderBy(SortType sortType){
        String orderBy = " order by ";
        switch (sortType){
            case minMax: orderBy += "item.price asc"; break;
            case maxMin: orderBy += "item.price desc"; break;
            case newDate: orderBy += "item.dateModified desc"; break;
            case popular: orderBy += "item.hit desc"; break;
            default: orderBy = "";
        }
        return orderBy;
    }

    public List<Item> selectActiveByCategoryInIdsRangeAndPriceLimitsOrderBySortType(
            int categoryId, int fromIndex, int toIndex, Double nativeMinPrice, Double nativeMaxPrice, SortType sortType, List<Integer> itemIds){
        String orderBy = getOrderBy(sortType);
        TypedQuery<Item> query = entityManager.createQuery("SELECT item from Item item where item.active = true and" +
                " (item.category.id = :catId or item.category.parent.id = :catId ) " +
//                " (item.category.id = :catId or item.category.parent.id = :catId ) and item.id >= :fromId and item.id <= :toId" +
                " and item.price >= :minPrice and item.price <= :maxPrice and item.id in (:itemIds) " + orderBy, Item.class);
        query.setParameter("catId", categoryId);
//        query.setParameter("fromId", fromIndex);
//        query.setParameter("toId", toIndex);
        query.setParameter("minPrice", nativeMinPrice);
        query.setParameter("maxPrice", nativeMaxPrice);
        query.setParameter("itemIds", itemIds);
        query.setFirstResult(fromIndex);
        query.setMaxResults(toIndex - fromIndex);
        List<Item> resultList = query.getResultList();
        return resultList;
    }

    public Item selectLastById() {
       return selectById(selectMaxId());
    }

    public Integer selectMaxId() {
        return (int) entityManager.createNativeQuery("SELECT max(id) from item").getSingleResult();
    }


//    @Transactional
    public List<Item> search(String input) {

        TypedQuery<Item> query = entityManager.createQuery(
                "select item from Item item where (item.title like :input or item.productCode like :input) and item.active = true order by item.dateModified desc", Item.class);
        query.setParameter("input", "%" + input + "%");
        query.setMaxResults(7);

        return query.getResultList();



//        QueryBuilder queryBuilder = searchFactory.buildQueryBuilder().forEntity(Item.class).get();
//
//        org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword().onFields("title", "description").matching(input).createQuery();
//
//        Query query = fullTextEntityManager.createFullTextQuery(luceneQuery, Item.class);
//
//        return query.getResultList();
    }

    @Transactional
    public List<Item> selectAllWithoutId(int selectedItemId) {
        TypedQuery<Item> query = entityManager.createQuery("SELECT item from Item item where item.id <> :itemId ", Item.class);
        query.setParameter("itemId", selectedItemId);
        return query.getResultList();
    }

    @Transactional
    public List<Item> selectAllActiveWithoutId(int selectedItemId, Boolean showNotActive) {
        if(showNotActive){
            return selectAllWithoutId(selectedItemId);
        }
        TypedQuery<Item> query = entityManager.createQuery("SELECT item from Item item where item.id <> :itemId and item.active = true ", Item.class);
        query.setParameter("itemId", selectedItemId);
        return query.getResultList();
    }

    public List<Item> selectAllOrderByActiveAndModified(Boolean showNotActive) {
        TypedQuery<Item> query;
        if(showNotActive)
            query = entityManager.createQuery("SELECT item from Item item order by item.active desc, item.dateModified desc, item.orderId desc ", Item.class);
        else
            query = entityManager.createQuery("SELECT item from Item item where item.active = true order by item.active desc, item.dateModified desc, item.orderId desc ", Item.class);
        return query.getResultList();
    }

    public List<Item> selectByCategoryOrderByActiveAndModified(Boolean showNotActive, int categoryId) {
        TypedQuery<Item> query;
        if(showNotActive)
            query = entityManager.createQuery("SELECT item from Item item where item.category.id = :catId" +
                    " order by item.active desc, item.dateModified desc, item.orderId desc ", Item.class);
        else
            query = entityManager.createQuery("SELECT item from Item item where item.category.id = :catId and " +
                    "item.active = true order by item.active desc, item.dateModified desc, item.orderId desc ", Item.class);
        query.setParameter("catId", categoryId);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Item> select8ActiveHitItems() {
        TypedQuery<Item> query = entityManager.createQuery("SELECT item from Item item" +
                " where item.active = true and item.hit = true order by item.active desc, item.dateAdd desc ", Item.class);
        query.setMaxResults(16);
        return query.getResultList();
    }

    public Item selectActiveItemById(int itemId) {
        TypedQuery<Item> query = entityManager.createQuery("select item from Item item where item.id = :itemId and item.active = true", Item.class);
        query.setParameter("itemId", itemId);
        try{
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    public Double selectMinPriceOfActiveItemByCategory(int categoryId) {
        TypedQuery<Double> query = entityManager.createQuery("SELECT min(item.price) from Item item where item.active = true and" +
                " (item.category.id = :catId or item.category.parent.id = :catId ) ", Double.class);
        query.setParameter("catId", categoryId);
        try {
            Double result = query.getSingleResult();
            if (result==null) return 0.0;
            return result;
        } catch (NoResultException e){
            return 0.0;
        }
    }

    public Double selectMaxPriceOfActiveItemByCategory(int categoryId) {
        TypedQuery<Double> query = entityManager.createQuery("SELECT max(item.price) from Item item where item.active = true and" +
                " (item.category.id = :catId or item.category.parent.id = :catId ) ", Double.class);
        query.setParameter("catId", categoryId);
        try {
            Double result = query.getSingleResult();
            if (result==null) return 0.0;
            return result;
        } catch (NoResultException e){
            return 0.0;
        }
    }
}
