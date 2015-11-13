package com.vasax.clothes.managed.pageFront.itemPage;

import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.Category;
import com.vasax.clothes.managed.CartBean;
import com.vasax.clothes.managed.LoginBean;
import com.vasax.clothes.managed.pageFront.Page;
import com.vasax.clothes.service.CategoryService;
import com.vasax.clothes.service.ImageService;
import com.vasax.clothes.service.ItemService;
import com.vasax.clothes.service.RelativeItemService;
import org.springframework.context.annotation.Scope;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;

/**
 * Created by vasax32 on 03.11.14.
 */
@Named
@Scope("view")
public class ItemPageBean extends Page{
    @Inject
    private ItemService itemService;

    @Inject
    private ImageService imageService;

    @Inject
    private RelativeItemService relativeItemService;

    @Inject
    private CartBean cartBean;

    @Inject
    private LoginBean loginBean;

    private Item item;

    private Category category;

    private int itemId;
    private long countOfImages;
    private List<Integer> imageIds;
    private List<String> colors;
    private List<Item> relatives;
    private String selectedColor = "";
    private int selectedColorIndex = -1;
    private int itemCount = 1;

    //@PostConstruct
    public void init() throws IOException {
        item = itemService.getActiveItemById(itemId);
        if(item == null){
            //redirect
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect("index.xhtml");
        } else {
            category = item.getCategory();
            countOfImages = imageService.getCountOfImagesByItem(itemId);
            imageIds = imageService.getIdsOfImages(itemId);
            colors = imageService.getColorsWithNotNullIdByItemId(itemId);
            if (!colors.isEmpty()) {
                selectedColor = colors.get(0);
                selectedColorIndex = imageService.getColorIdByItemIdAndColor(itemId, selectedColor);
            }

            //relative items
            relatives = relativeItemService.getRelativesByItem(itemId);

            //for viewed items
            loginBean.getViewedItems().add(item);

            pageComponent = pageComponentService.require("item");
            components = componentService.getAllByPageComponentAsMap(pageComponent.getId());
        }
    }

    public int getColorId(String selectedColor){
        return imageService.getColorIdByItemIdAndColor(itemId, selectedColor);
    }

    public void addToCart(){
        System.out.println("Add to Cart: Items count: " + itemCount);
        cartBean.addToCart(itemId, itemCount);
    }

    public int getFirstId(){
        return imageIds.get(0);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getCountOfImages() {
        return countOfImages;
    }

    public void setCountOfImages(long countOfImages) {
        this.countOfImages = countOfImages;
    }

    public List<Integer> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<Integer> imageIds) {
        this.imageIds = imageIds;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<Item> getRelatives() {
        return relatives;
    }

    public void setRelatives(List<Item> relatives) {
        this.relatives = relatives;
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getSelectedColorIndex() {
        return selectedColorIndex;
    }

    public void setSelectedColorIndex(int selectedColorIndex) {
        this.selectedColorIndex = selectedColorIndex;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
