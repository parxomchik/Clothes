package com.vasax.clothes.service;

import com.vasax.clothes.dao.CategoryDao;
import com.vasax.clothes.entities.Category;
import com.vasax.clothes.entities.Item;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oper4 on 03.11.2014.
 */
@Named
public class CategoryService {
    @Inject
    CategoryDao categoryGenericDao;

    public List<Category> getAllCategories(){
        return new ArrayList<>(categoryGenericDao.selectAll());
    }

    public List<Category> getAllCategoriesDetached(){
        return new ArrayList<>(categoryGenericDao.selectAllDetached());
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(int id){
        return categoryGenericDao.selectById(id);
    }

    @Transactional(readOnly = true)
    public List<Item> getItemsByCategoryId(int id){
        Category category = categoryGenericDao.selectById(id);
        return new ArrayList<>(category.getItems()); //why we need to do this?
    }

    @Transactional
    public void update(List<Category> categories) {
        for(Category category : categories) {
            Category update = categoryGenericDao.update(category);
            if(update.getOrderId() == -1){
                update.setOrderId(update.getId());
                //categoryGenericDao.update(category);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Category> getActiveCategories(){
        return categoryGenericDao.selectActive();
    }

    @Transactional(readOnly = true)
    public List<Category> getMainActiveCategories(){
        return new ArrayList<>(categoryGenericDao.selectMainAndActive());
    }

    @Transactional(readOnly = true)
    public List<Category> getSubActiveCategoriesByMainId(int id){
        return new ArrayList<>(categoryGenericDao.selectSubActiveByMainId(id));
    }

    @Transactional(readOnly = true)
    public List<Category> getAllSubCategories(){
        return categoryGenericDao.selectAllSub();
    }

    @Transactional
    public Category getActiveCategoryById(int categoryId) {
        return categoryGenericDao.selectActiveById(categoryId);
    }
}
