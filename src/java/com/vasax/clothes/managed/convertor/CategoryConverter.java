package com.vasax.clothes.managed.convertor;

import com.vasax.clothes.entities.Category;
import com.vasax.clothes.service.CategoryService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16.11.14.
 */
@Named
//@Scope("request")
@Scope("view")
//@FacesConverter(forClass = Category.class,value="categoryConverter")
public class CategoryConverter implements Converter {

    public Map<Integer, Category> categories = new HashMap<>();

    @Inject
    private CategoryService categoryService;

    @PostConstruct
    public void init(){
        List<Category> categoriesList = categoryService.getAllCategories();
        for (Category category : categoriesList)
            categories.put(category.getId(), category);
    }

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
//        System.out.println("Category converter gets as object");
        PhaseId currentPhaseId = FacesContext.getCurrentInstance().getCurrentPhaseId();
//        System.out.println(currentPhaseId);
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);

                Category category = categories.get(number);
                int x = 5;
//                System.out.println("Submitted value: " + submittedValue + " : " + category);
                return category;

            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));
            }
        }
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            Category category = (Category) value;
            return String.valueOf(category.getId());
        }
    }

    public Map<Integer, Category> getCategories() {
        return categories;
    }

    public void setCategories(Map<Integer, Category> categories) {
        this.categories = categories;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
