package com.vasax.clothes.managed;

import com.vasax.clothes.entities.Category;
import com.vasax.clothes.service.CategoryService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vasax32 on 13.04.15.
 */
@Named
@Scope("view")
public class CategoryBean {
    private List<Category> categories;

    @Inject
    private CategoryService categoryService;
    private HashMap<Integer, List<Category>> subCategories;

    @PostConstruct
    public void init(){
        categories = categoryService.getMainActiveCategories();
        subCategories = new HashMap<>();
        for (Category category : categories) {
            int mainCategoryId = category.getId();
            category.getTitle();
            List<Category> subCategoriesList = categoryService.getSubActiveCategoriesByMainId(mainCategoryId);
            if (subCategoriesList != null && subCategoriesList.size() != 0) {
                subCategories.put(mainCategoryId, subCategoriesList);
            }
        }
    }

    public List<Category> getSubCategoriesByMainId(int mainCategoryId){
        return subCategories.get(mainCategoryId);
    }

    public boolean isSubCategoryPresent(int mainCategoryId){
        boolean b = subCategories.containsKey(new Integer(mainCategoryId));
        return b;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public HashMap<Integer, List<Category>> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(HashMap<Integer, List<Category>> subCategories) {
        this.subCategories = subCategories;
    }
}
