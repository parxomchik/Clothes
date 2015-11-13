package com.vasax.clothes.service;

import com.vasax.clothes.dao.AttributeDao;
import com.vasax.clothes.dao.CategoryDao;
import com.vasax.clothes.entities.Attribute;
import com.vasax.clothes.entities.Category;
import com.vasax.clothes.entities.ItemAttributeProperty;
import com.vasax.clothes.entities.Property;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * Created by vasax32 on 09.04.15.
 */
@Named
public class AttributeService {
    @Inject
    private AttributeDao attributeDao;
    @Inject
    private CategoryDao categoryDao;
    @Inject
    private PropertyService propertyService;
    @Inject
    private ItemAttributePropertyService itemAttributePropertyService;

    @Transactional(readOnly = true)
    public LinkedHashMap<Attribute, List<Property>> getAttributesWithPropertiesByCategoryRecursively(int categoryId){
        LinkedHashMap<Attribute, List<Property>> rez = new LinkedHashMap<>();
        List<Attribute> attributesByCategoryRecursively = getAttributesByCategoryRecursively(categoryId);
        for (Attribute attribute : attributesByCategoryRecursively) {
            List<Property> propertiesByAttribute = propertyService.getPropertiesByAttribute(attribute.getId());
            Set<Property> filteredProperties = new HashSet<>();
            //need to filter properties by usage
            List<Category> subCategories = categoryDao.selectSubActiveByMainId(categoryId);
            for (Property property : propertiesByAttribute) {
                List<ItemAttributeProperty> byPropertyAndCategoryId = itemAttributePropertyService.getByPropertyAndCategoryId(property, attribute, categoryId);
                if(byPropertyAndCategoryId.size() > 0) {
                    filteredProperties.add(property);
                    continue;
                }

                for (Category subCategory : subCategories) {
                    List<ItemAttributeProperty> byPropertyAndCategoryId1 = itemAttributePropertyService.getByPropertyAndCategoryId(property, attribute, subCategory.getId());
                    if(byPropertyAndCategoryId1.size() > 0)
                        filteredProperties.add(property);
                }

            }

            //filter if this is main category -we need to remove attributes from sub categories
            Set<Property> filteredPropertiesWithMainCategoryFix = new HashSet<>();
            Category category = categoryDao.selectById(categoryId);
            if(category.getParent() == null){
                for (Property filteredProperty : filteredProperties) {
                    if(filteredProperty.getAttribute().getCategory().getId() == categoryId){
                        filteredPropertiesWithMainCategoryFix.add(filteredProperty);
                    }
                }
            } else
                filteredPropertiesWithMainCategoryFix.addAll(filteredProperties);

            if(filteredPropertiesWithMainCategoryFix.size() > 0)
                rez.put(attribute, new ArrayList<>(filteredPropertiesWithMainCategoryFix));
        }


        return rez;
    }

    @Transactional(readOnly = true)
    public List<Attribute> getAttributesByCategoryRecursively(int categoryId) {
        Set<Attribute> allAttributes = new HashSet<>();
        Category category = categoryDao.selectById(categoryId);
        allAttributes.addAll(getActiveAttributesByCategory(categoryId));
        //need to search in parent
        if(category.getParent() != null)
            allAttributes.addAll(getActiveAttributesByCategory(category.getParent().getId()));
        //need to find all sub categories down by tree
        List<Category> subCategories = categoryDao.selectSubActiveByMainId(categoryId);
        for (Category subCategory : subCategories) {
            allAttributes.addAll(getActiveAttributesByCategory(subCategory.getId()));
        }

        return new ArrayList<>(allAttributes);

//        return getAttributesRecursively(category);
    }

    private List<Attribute> getAttributesRecursively(Category category){
        List<Attribute> rez = new ArrayList<>();
        rez.addAll(getActiveAttributesByCategory(category.getId()));
        if(category.getParent() != null)
            rez.addAll(getAttributesRecursively(category.getParent()));
        return rez;
    }

    @Transactional(readOnly = true)
    public List<Attribute> getActiveAttributesByCategory(int id){
        List<Attribute> activeByCategoryId = attributeDao.getActiveByCategoryId(id);
        List<Attribute> ret = new ArrayList<>();

        for (Attribute attribute : activeByCategoryId) {
            List<ItemAttributeProperty> iaps = itemAttributePropertyService.getByAttributeIdAndCategoryId(attribute, id);
            if(iaps.size() > 0)
                ret.add(attribute);
        }

        return ret;
    }

    @Transactional
    public List<Attribute> getByCategoryId(int id){
        return attributeDao.getByCategoryId(id);
    }

    @Transactional(readOnly = true)
    public Attribute getById(int id){
        return attributeDao.selectById(id);
    }

    @Transactional
    public List<Attribute> update(List<Attribute> attributes) {
        List<Attribute> ret = new ArrayList<>();
        for (Attribute attribute : attributes) {
            ret.add(attributeDao.update(attribute));
        }
        return ret;
    }

    @Transactional
    public Attribute add(Attribute attribute) {
        return attributeDao.insert(attribute);
    }

    @Transactional
    public Attribute update(Attribute attribute) {
        return attributeDao.update(attribute);
    }

    @Transactional
    public void removeById(int id) {
        Attribute byId = getById(id);
        if(byId != null) {
            List<Property> propertiesByAttribute = propertyService.getPropertiesByAttribute(id);
            for (Property property : propertiesByAttribute) {
                propertyService.removeById(property.getId());
            }
            attributeDao.deleteByObj(byId);
        }
    }
}
