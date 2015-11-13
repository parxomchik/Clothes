package com.vasax.clothes.service;

import com.vasax.clothes.dao.ItemAttributePropertyDao;
import com.vasax.clothes.dao.ItemDao;
import com.vasax.clothes.entities.*;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vasax32 on 12.04.15.
 */
@Named
public class ItemAttributePropertyService {
    @Inject
    private ItemAttributePropertyDao itemAttributePropertyDao;
    @Inject
    private ItemDao itemDao;

//    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Transactional
    public List<ItemAttributeProperty> getWithNullsByItemId(int itemId){
        List<ItemAttributeProperty> ret = new ArrayList<>();

        List<ItemAttributeProperty> iapExisted = itemAttributePropertyDao.getByItemId(itemId);
        Item item = itemDao.selectById(itemId);
        Map<Attribute, ItemAttributeProperty> aiapExistedMap = new HashMap<>();
        for (ItemAttributeProperty itemAttributeProperty : iapExisted) {
            aiapExistedMap.put(itemAttributeProperty.getAttribute(), itemAttributeProperty);
        }

        List<Attribute> attributesFromCategory = getAttributesFromCategoryRecursively(item.getCategory());

        if(attributesFromCategory.size() == iapExisted.size()){
            ret.addAll(iapExisted);
            return ret;
        } else {
            for (Attribute attribute : attributesFromCategory) {
                if(aiapExistedMap.containsKey(attribute)){
                    ret.add(aiapExistedMap.get(attribute));
                } else {
                    //construct nullable property
                    ItemAttributeProperty iap = new ItemAttributeProperty();
                    iap.setItem(item);
                    iap.setAttribute(attribute);
                    ret.add(iap);
                }
            }
            return ret;
        }
    }

    private List<Attribute> getAttributesFromCategoryRecursively(Category category){
        List<Attribute> attributesFromCategory = new ArrayList<>(category.getAttributes());
        Category parent = category.getParent();
        if(parent != null){
            attributesFromCategory.addAll(getAttributesFromCategoryRecursively(parent));
        }
        return attributesFromCategory;
    }

    @Transactional
    public void updateProperty(List<ItemAttributeProperty> iaps) {
        for (ItemAttributeProperty iap : iaps) {
            if(iap.getProperty() != null){
                ItemAttributeProperty selectedIap = itemAttributePropertyDao.getByItemIdAndAttributeId(iap.getItem().getId(), iap.getAttribute().getId());
                if(selectedIap != null){
                    selectedIap.setProperty(iap.getProperty());
                    itemAttributePropertyDao.update(selectedIap);
                } else {
                    //iap.getItem().getItemAttributeProperties().add(iap);
                    iap.setItem(itemDao.selectById(iap.getItem().getId()));
                    itemAttributePropertyDao.insert(iap);
                }
            } else {
                ItemAttributeProperty selectedIap = itemAttributePropertyDao.getByItemIdAndAttributeId(iap.getItem().getId(), iap.getAttribute().getId());
                if(selectedIap != null) {
//                    selectedIap.getItem().getItemAttributeProperties().remove(selectedIap);
                    itemAttributePropertyDao.remove(selectedIap);
                }
            }
        }
    }

    @Transactional
    public List<ItemAttributeProperty> getByPropertyId(int id) {
        return itemAttributePropertyDao.getByPropertyId(id);
    }

    @Transactional
    public void deleteByObj(ItemAttributeProperty itemAttributeProperty) {
        itemAttributePropertyDao.remove(itemAttributeProperty);
    }

    @Transactional
    public List<ItemAttributeProperty> getByAttributeIdAndCategoryId(Attribute attribute, int categoryId) {
        return itemAttributePropertyDao.getByAttributeIdAndCategoryOrParentCategory(attribute.getId(), categoryId);
    }

    @Transactional
    public List<ItemAttributeProperty> getByPropertyAndCategoryId(Property property, Attribute attribute, int categoryId) {
        return itemAttributePropertyDao.getByPropertyAndAttributeAndCategoryOrParentCategory(attribute.getId(), property.getId(), categoryId);
    }
}
