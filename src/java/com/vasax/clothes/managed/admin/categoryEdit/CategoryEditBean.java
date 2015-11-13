package com.vasax.clothes.managed.admin.categoryEdit;

import com.vasax.clothes.entities.Category;
import com.vasax.clothes.managed.convertor.CategoryConverter;
import com.vasax.clothes.service.CategoryService;
import javafx.util.Pair;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ReorderEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.*;

/**
 * Created by root on 16.11.14.
 */
@Named
@Scope("view")
public class CategoryEditBean {

    @Inject
    private CategoryService categoryService;
    @Inject
    private CategoryModalEditBean categoryModalEditBean;
    @Inject
    private CategoryConverter categoryConverter;

    private List<Category> categories;
    private List<Category> savedCategories;
    private Map<Integer, Category> categoriesMap = new HashMap<>();
    private List<Category> categoryForItems;


    @PostConstruct
    public void init(){
        categories = categoryService.getAllCategories();
        savedCategories = new ArrayList<>(categories);
        for (Category category : categories)
            categoriesMap.put(category.getId(), category);

        categoryForItems = categoryService.getAllSubCategories();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getSavedCategories() {
        return savedCategories;
    }

    public void setSavedCategories(List<Category> savedCategories) {
        this.savedCategories = savedCategories;
    }

    public void selectCategory(int id){
        categoryModalEditBean.setSelectedCategory(categoriesMap.get(id));
    }

    public List<Category> getCategoryForItems() {
        return categoryForItems;
    }

    public void setCategoryForItems(List<Category> categoryForItems) {
        this.categoryForItems = categoryForItems;
    }

    public List<Category> getAvailableCategories(int orderId){
//        System.out.println("Getting available categories for id: " + orderId);
        List<Category> cats = new ArrayList<>();
        //need to add null category
        Category e = new Category();
        e.setId(-1);
        e.setTitle("NULL");
        cats.add(e);

//        cats.addAll(savedCategories);
//        for (Category savedCategory : savedCategories) {
        for (Category savedCategory : categories) {
            //if(savedCategory.getParent() == null)
                cats.add(savedCategory);
        }
//        //need to remove itself
//        for (Category category : categories) {
//            if (category.getOrderId() == orderId) {
//                cats.remove(category);
//            }
//        }
        return cats;
    }

    public void onRowReorder(ReorderEvent event) {
        int fromIndex = event.getFromIndex();
        int toIndex = event.getToIndex();
        Category getFrom = categories.get(fromIndex);
        Category getTo = categories.get(toIndex);
        int temp = getFrom.getOrderId();
        getFrom.setOrderId(getTo.getOrderId());
        getTo.setOrderId(temp);

        Collections.swap(categories, fromIndex, toIndex);
        save();
//        System.out.println("Category converter set new value");
        //add message
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Category Moved", "From: " + getFrom.getId()+ ", To:" + getTo.getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String save(){
        categoryService.update(categories);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        reload();
        return null;
    }

    public void upload(FileUploadEvent event) throws IOException {
        int id = (Integer) event.getComponent().getAttributes().get("categoryId");
        UploadedFile file = event.getFile();
        byte[] contents = IOUtils.toByteArray(file.getInputstream());

        Category category = categoriesMap.get(id);
        category.setImage(contents);
        category.setImageName(file.getFileName());
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Image uploaded","File: " + file.getFileName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void removeImage(int id){
        Category category = categoriesMap.get(id);
        category.setImage(null);
        category.setImageName("");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Image removed","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String reload(){
        categoriesMap.clear();
        init();
        return "";
    }

    public String addNew(){
        Category category = new Category();
        category.setId(getRandomId());
        categories.add(category);
        categoriesMap.put(category.getId(), category);
        return "";
    }

    private int getRandomId(){
        Random r = new Random();
        int x = 0;
        while (true) {
            x = r.nextInt(categories.size() + 100);
            boolean notIn = true;
            for(Category category : categories)
                if(category.getId() == x)
                    notIn = false;
            if(notIn)
                break;

        }
        return x;
    }
}
