package com.vasax.clothes.service;

import com.vasax.clothes.dao.AttributeDao;
import com.vasax.clothes.dao.PropertyDao;
import com.vasax.clothes.entities.ItemAttributeProperty;
import com.vasax.clothes.entities.Property;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasax32 on 10.04.15.
 */
@Named
public class PropertyService {
    @Inject
    private PropertyDao propertyDao;
    @Inject
    private AttributeDao attributeDao;
    @Inject
    private AttributeService attributeService;
    @Inject
    private ItemAttributePropertyService itemAttributePropertyService;

    @Transactional(readOnly = true)
    public List<Property> getPropertiesByAttribute(int id){
        return propertyDao.selectByAttribute(id);
    }

    @Transactional
    public List<Property> update(List<Property> properties) {
        List<Property> ret = new ArrayList<>();
        for (Property property : properties) {
            ret.add(propertyDao.update(property));
        }
        return ret;
    }

    @Transactional(readOnly = true)
    public Property getById(int id) {
        return propertyDao.selectById(id);
    }

    @Transactional
    public void removeById(int id) {
        Property byId = getById(id);
        if(byId != null) {
            List<ItemAttributeProperty> byPropertyId = itemAttributePropertyService.getByPropertyId(id);
            for (ItemAttributeProperty itemAttributeProperty : byPropertyId) {
                itemAttributePropertyService.deleteByObj(itemAttributeProperty);
            }
            propertyDao.deleteByObj(byId);
        }
    }
}
