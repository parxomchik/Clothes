package com.vasax.clothes.managed.pageFront;

import com.vasax.clothes.entities.Attribute;
import com.vasax.clothes.entities.Category;
import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.Property;
import com.vasax.clothes.entities.enums.SortType;
import com.vasax.clothes.managed.admin.frontSiteSetting.FrontSiteSettings;
import com.vasax.clothes.service.AttributeService;
import com.vasax.clothes.service.CategoryService;
import com.vasax.clothes.service.ImageService;
import com.vasax.clothes.service.ItemService;
import org.springframework.context.annotation.Scope;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by vasax32 on 20.04.15.
 */
@Named
//@Scope("view")
@Scope("session")
public class FilterPageBean {
    private int categoryId;
    private List<Item> items;
    private List<Integer> itemIds;
//    private List<Item> filteredItems;
    private Category category;
    private Category parentCategory;
    private LinkedHashMap<Attribute, List<Property>> attributeProperties;
    private List<String> colors;
    private String[] selectedColors;
    private Map<Integer, String[]> filters = new HashMap<>(); //Attribute/Property
    private Integer minPrice = 10;
    private Double nativeMinPrice;
    private Integer maxPrice = 1000;
    private Double nativeMaxPrice;
    private SortType sortType = SortType.minMax;
    private int pageId = 0;
    private int itemsPerPage = 5 * 6;
//    private int itemsPerPage = 4;
    private int oldCategoryId = -1;

    @Inject
    private ItemService itemService;
    @Inject
    private CategoryService categoryService;
    @Inject
    private AttributeService attributeService;
    @Inject
    private ImageService imageService;
    @Inject
    private FrontSiteSettings frontSiteSettings;

    public void init() throws IOException {
        category = categoryService.getActiveCategoryById(categoryId);
        if(category == null){
            //redirect
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } else {
            parentCategory = category.getParent();

//            items = itemService.getActiveItemsByCategory(categoryId);
//            if(!itemIds.isEmpty())
            {
                nativeMinPrice = itemService.getMinPriceOfActiveItemByCategory(categoryId);
                minPrice = frontSiteSettings.getPriceInCurrentCurrency(nativeMinPrice).intValue();
                nativeMaxPrice = itemService.getMaxPriceOfActiveItemByCategory(categoryId);
                maxPrice = frontSiteSettings.getPriceInCurrentCurrency(nativeMaxPrice).intValue();
            }

//        pageId = 1;


            if(oldCategoryId != -1 || oldCategoryId != category.getId()){
                //need to reset filter
                selectedColors = new String[]{};
                if(filters != null)filters.clear();
                oldCategoryId = category.getId();
            }

            loadIds();
            loadItems();

            if(pageId -1 > items.size())
                pageId = 1;
//            filteredItems = new ArrayList<>(items);
            priceLimiterChange();
            attributeProperties = attributeService.getAttributesWithPropertiesByCategoryRecursively(categoryId);
            colors = imageService.getAllColorsByCategoryId(categoryId);

            sort();

        }
    }

    private void loadIds(){
        if(!isFilterEmpty() && filters != null) {
            //need to clear filters
            Iterator<Integer> iterator = filters.keySet().iterator();
            List<Integer> toRemove = new ArrayList<>();
            while (iterator.hasNext()) {
                Integer attributeId = iterator.next();
                String[] propertyIds = filters.get(attributeId);
                if(propertyIds.length == 0){
                    toRemove.add(attributeId);
                }
            }
            for (Integer attributeId : toRemove) {
                filters.remove(attributeId);
            }
            itemIds = itemService.getFilteredItemsWithColors(categoryId, filters, selectedColors, nativeMinPrice, nativeMaxPrice);
        }
        else
            itemIds = itemService.getActiveItemIdsByCategoryOrderBySortType(categoryId, nativeMinPrice, nativeMaxPrice);
    }

    public void loadItems(){
        handleItemsChange();
        if(itemIds.size() > 0) {
            int fromIndex = pageId * itemsPerPage;
            int toIndex = (((pageId + 1) * itemsPerPage));
            toIndex = toIndex > itemIds.size() ? itemIds.size() : toIndex;
            items = itemService.getActiveItemsByCategoryInIndexRangeAndPriceLimitsOrderBySortType(
                    categoryId, fromIndex, toIndex, nativeMinPrice, nativeMaxPrice, sortType, itemIds);
        } else
            items = new ArrayList<>();
    }

    public int getCurrentPageNum(){
        return pageId + 1;
    }

    public void changeListener(){
//        loadIds();
//        loadItems();
//        filteredItems = new ArrayList<>(items);
        priceLimiterChange();
//        sort();
    }

    private boolean isFilterEmpty(){
        for (Integer attributeId : filters.keySet()){
            String[] s = filters.get(attributeId);
            if(s.length > 0)
                return false;
        }
        if(selectedColors != null && selectedColors.length > 0)
            return false;
        return true;
    }

    public void priceLimiterChange(){
//        List<Item> itemsTemp = new ArrayList<>(items);
//        items.clear();
//        for (Item filteredItem : itemsTemp) {
//            int price = frontSiteSettings.getPriceInCurrentCurrency(filteredItem.getPrice()).intValue();
//            if(price >= minPrice && price <= maxPrice)
//                items.add(filteredItem);
//        }
        nativeMinPrice = frontSiteSettings.getPriceInNativeCurrency(minPrice);
        nativeMaxPrice = frontSiteSettings.getPriceInNativeCurrency(maxPrice) + 0.03;
        handleItemsChange();
//        sort();
        loadIds();
        loadItems();
    }

    public void handleItemsChange(){
        //for pages
        int countOfAvailablePages = getCountOfAvailablePages();
        if((pageId + 1) > countOfAvailablePages){
            pageId = countOfAvailablePages - 1;
            if(pageId <0) pageId = 0;
        }
    }

    public void sort(){
        switch (sortType){
            case minMax: sortFromMinPriceToMax(); break;
            case maxMin: sortFromMaxPriceToMin(); break;
            case newDate: sortNewDate(); break;
            case popular: sortPopular(); break;
        }
    }

    public void sortFromMinPriceToMax(){
        sortType = SortType.minMax;
//        System.out.println("Sort min -> max");

//        Collections.sort(items, Item.minMaxPriceComparator);
        loadIds();
        loadItems();
    }

    public void sortFromMaxPriceToMin(){
        sortType = SortType.maxMin;
//        System.out.println("Sort max -> min");

//        Collections.sort(items, Item.maxMinPriceComparator);
        loadIds();
        loadItems();
    }

    public void sortNewDate(){
        sortType = SortType.newDate;
//        System.out.println("Sort new date");

//        Collections.sort(items, Item.newDateComparator);
        loadIds();
        loadItems();
    }

    public void sortPopular(){
        sortType = SortType.popular;
//        System.out.println("Sort popular");

//        Collections.sort(items, Item.popularHitComparator);
        loadIds();
        loadItems();
    }

    public void resetFilters(){
        System.out.println("Reset filters");
        filters.clear();
        selectedColors = new String[]{};
        changeListener();
    }

    public int getCountOfAvailablePages(){
        int size = itemIds.size();
        int pages = size /itemsPerPage;
        if(size % itemsPerPage != 0)
            pages++;
        return pages;
    }



    public boolean isNewItem(Timestamp lastModified){
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(lastModified);
        cal.add( Calendar.DAY_OF_YEAR, 7);
        Date sevenDaysAfter = cal.getTime();
        return new Date().compareTo(sevenDaysAfter) < 0;
    }

    public void changePage(int pageId){
        if(pageId >=0 & pageId < getCountOfAvailablePages())
            this.pageId = pageId;
        loadItems();
    }

    public List<Attribute> getAttributes(){
        return new ArrayList<>(attributeProperties.keySet());
    }

    public List<Property> getPropertiesByAttribute(Attribute attribute){
        return attributeProperties.get(attribute);
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public List<Item> getItems() {
//        int fromIndex = pageId * itemsPerPage;
//        int toIndex = (((pageId + 1) * itemsPerPage));
//        List<Item> items1 = items.subList(fromIndex, toIndex > items.size() ? items.size() : toIndex);
//        return items1;
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public LinkedHashMap<Attribute, List<Property>> getAttributeProperties() {
        return attributeProperties;
    }

    public void setAttributeProperties(LinkedHashMap<Attribute, List<Property>> attributeProperties) {
        this.attributeProperties = attributeProperties;
    }

    public Map<Integer, String[]> getFilters() {
        return filters;
    }

    public void setFilters(Map<Integer, String[]> filters) {
        this.filters = filters;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public String[] getSelectedColors() {
        return selectedColors;
    }

    public void setSelectedColors(String[] selectedColors) {
        this.selectedColors = selectedColors;
    }
}

