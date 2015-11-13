package com.vasax.clothes.service;

import com.vasax.clothes.dao.ItemAttributePropertyDao;
import com.vasax.clothes.dao.ItemDao;
import com.vasax.clothes.dao.ItemImageDao;
import com.vasax.clothes.entities.*;
import com.vasax.clothes.dao.OrderItemDao;
import com.vasax.clothes.entities.enums.SortType;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by oper4 on 03.11.2014.
 */
@Named
public class ItemService {
    @Inject
    private ItemDao itemDao;

    @Inject
    private OrderItemDao orderItemDao;

    @Inject
    private ItemAttributePropertyDao itemAttributePropertyDao;

    @Inject
    private ItemImageDao itemImageDao;

    @Inject
    private GlobalSettingService globalSettingService;

    @Transactional(readOnly = true)
    public Item getItemById(int id){
        return itemDao.selectById(id);
    }

    @Transactional(readOnly = true)
    public Category getCategoryByItem(Item item){
        int id = item.getId();
        return item.getCategory();
    }

    @Transactional(readOnly = true)
    public List<Item> getAllItems(){
        return new ArrayList<>(itemDao.selectAll());
    }

    @Transactional(readOnly = true)
    public List<Item> getAllItemsOrderByActiveAndDateModified(){
        Boolean showNotActive = globalSettingService.requireBoolean("showNotActive");
        return new ArrayList<>(itemDao.selectAllOrderByActiveAndModified(showNotActive));
    }

    @Transactional(readOnly = true)
    public List<Item> getActiveItemsByCategory(int categoryId){
        return itemDao.selectActive(categoryId);
    }

    @Transactional(readOnly = true)
    public List<Integer> getActiveItemIdsByCategoryOrderBySortType(int categoryId, Double nativeMinPrice, Double nativeMaxPrice){
        return itemDao.selectActiveIdsOrderBySortType(categoryId, nativeMinPrice, nativeMaxPrice);
    }

    @Transactional
    public List<Item> update(List<Item> items) {
        List<Item> ret = new ArrayList<>();
        for (Item item : items) {
            boolean changed = isChanged(item);
//            Item updated = itemDao.update(item);
            boolean needToPersist = false;
            if(item.getDateAdd() == null) {
                item.setDateAdd(new Timestamp(new Date().getTime()));
                needToPersist = true;
            }
            if(item.getDateModified() == null || changed) {
                item.setDateModified(new Timestamp(new Date().getTime()));
                needToPersist = true;
            }
            if(needToPersist) {
//                System.out.println("In block needToPersist - ITEM: "+item);
                item = itemDao.update(item);
            }

            //for orderId
            if(item.getOrderId() == 0){
                item.setOrderId(item.getId());
//                System.out.println("In block ForOrderID - ITEM: "+item);
                item = itemDao.update(item);
            }

            ret.add(item);

        }
        return ret;
    }

    @Transactional
    public List<Item> insertItems(List<Item> newItems) {
        List<Item> resultList = new ArrayList<>();
        for (Item i :newItems) {
            i.setOrderId(i.getId());
            i.setId(0);
            i.setDateAdd(new Timestamp(new Date().getTime()));
            i.setDateModified(new Timestamp(new Date().getTime()));
            resultList.add(itemDao.insert(i));
        }
        return resultList;
    }

    private boolean isChanged(Item item){
        Item fromDb = itemDao.selectById(item.getId());
        return !item.equals(fromDb);
    }

    public List<Item> getDishesByOrderId(int orderId) {
        List<OrderItem> orderItems = orderItemDao.selectByOrderId(orderId);
        List<Item> items = new ArrayList<>();
        for(OrderItem orderItem : orderItems)
            items.add(orderItem.getItem());
        return items;
    }

    @Transactional(readOnly = true)
    public List<Item> search(String input){
        return itemDao.search(input);
    }

    @Transactional
    public List<Item> getAllItemsWithId(int selectedItemId) {
        return itemDao.selectAllWithoutId(selectedItemId);
    }

    @Transactional
    public List<Item> getAllActiveItemsWithouId(int selectedItemId) {
        Boolean showNotActive = globalSettingService.requireBoolean("showNotActive");
        return itemDao.selectAllActiveWithoutId(selectedItemId, showNotActive);
    }

    @Transactional(readOnly = true)
    public List<Integer> getFilteredItemsWithColors(int categoryId, Map<Integer, String[]> filters, String[] selectedColors, Double nativeMinPrice, Double nativeMaxPrice){
        List<Integer> rez = new ArrayList<>();

        List<Set<Integer>> items = new ArrayList<>();
        for (Integer attributeId : filters.keySet()){

            Set<Integer> itemIdsByAttribute = new HashSet<>();
            String[] properties = filters.get(attributeId);
            if(properties.length == 0)
                continue;
            for (String property : properties) {
                Integer propertyId = Integer.valueOf(property);
                itemIdsByAttribute.addAll(getIdsByCategoryIdAttributeIdPropertyId(categoryId, attributeId, propertyId, nativeMinPrice, nativeMaxPrice));
            }
            items.add(itemIdsByAttribute);
        }
        //resolving colors filter
        Set<Integer> colorItems = new HashSet<>();
        for (String color : selectedColors) {
            colorItems.addAll(getIdsByCategoryAndColor(categoryId, color, nativeMinPrice, nativeMaxPrice));
        }
        if(colorItems.size() > 0)
            items.add(colorItems);

        //need to retain
        if(items.size() > 0){
            rez.addAll(items.get(0));
            for(int i = 1; i< items.size(); i++){
                Set<Integer> toRetain = items.get(i);
                rez.retainAll(toRetain);
            }
        }

        return rez;
    }

    @Transactional(readOnly = true)
    public List<Integer> getIdsByCategoryIdAttributeIdPropertyId(int categoryId, int attributeId, int propertyId, Double nativeMinPrice, Double nativeMaxPrice){
        return itemAttributePropertyDao.selectActiveItemIdsWithFilter(categoryId, attributeId, propertyId, nativeMinPrice, nativeMaxPrice);
    }

    @Transactional(readOnly = true)
    public List<Integer> getIdsByCategoryAndColor(int categoryId, String color, Double nativeMinPrice, Double nativeMaxPrice){
        return itemImageDao.selectActiveItemIdsByCategoryAndColor(categoryId, color, nativeMinPrice, nativeMaxPrice);
    }

    public List<Item> get8ActiveHitItems() {
        return itemDao.select8ActiveHitItems();
    }

    @Transactional
    public Item update(Item item) {
        return itemDao.update(item);
    }

    @Transactional
    public Item getActiveItemById(int itemId) {
        return itemDao.selectActiveItemById(itemId);
    }

    @Transactional
    public Double getMinPriceOfActiveItemByCategory(int categoryId) {
        return itemDao.selectMinPriceOfActiveItemByCategory(categoryId);
    }

    @Transactional
    public Double getMaxPriceOfActiveItemByCategory(int categoryId) {
        return Math.ceil(itemDao.selectMaxPriceOfActiveItemByCategory(categoryId));
    }

    @Transactional
    public Item cloneItem(Item prototypeItem) {
//        Item prototypeItem = itemDao.selectById(itemId);
        Item item = new Item(prototypeItem);
        item.setId(0);
        itemDao.update(item);
        item = itemDao.selectLastById();
        List<ItemAttributeProperty> propList = new ArrayList<>();

//        for (ItemAttributeProperty iap : prototypeItem.getItemAttributeProperties()) {
        for (ItemAttributeProperty iap : itemAttributePropertyDao.getByItemId(prototypeItem.getId())) {
            ItemAttributeProperty entry = new ItemAttributeProperty();
            entry.setItem(item);
            entry.setAttribute(iap.getAttribute());
            entry.setProperty(iap.getProperty());
            propList.add(entry);
        }
        item.setItemAttributeProperties(propList);

        return item;
    }

    public List<Item> getByCategoryOrderByActiveAndDateModified(int categoryId) {
        Boolean showNotActive = globalSettingService.requireBoolean("showNotActive");
        return new ArrayList<>(itemDao.selectByCategoryOrderByActiveAndModified(showNotActive, categoryId));
    }

    public List<Item> getActiveItemsByCategoryInIndexRangeAndPriceLimitsOrderBySortType(
            int categoryId, int fromIndex, int toIndex, Double nativeMinPrice, Double nativeMaxPrice, SortType sortType, List<Integer> itemIds) {
        return itemDao.selectActiveByCategoryInIdsRangeAndPriceLimitsOrderBySortType(categoryId, fromIndex, toIndex, nativeMinPrice, nativeMaxPrice, sortType, itemIds);
    }

    public Integer getMaxId() {
       return itemDao.selectMaxId();
    }
}
