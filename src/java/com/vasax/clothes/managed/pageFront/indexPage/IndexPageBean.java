package com.vasax.clothes.managed.pageFront.indexPage;

import com.vasax.clothes.entities.Category;
import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.component.PageComponent;
import com.vasax.clothes.entities.component.PlainComponent;
import com.vasax.clothes.managed.pageFront.Page;
import com.vasax.clothes.service.CategoryService;
import com.vasax.clothes.service.ComponentService;
import com.vasax.clothes.service.ItemService;
import com.vasax.clothes.service.PageComponentService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * Created by vasax32 on 29.04.15.
 */
@Named
@Scope("view")
public class IndexPageBean extends Page{
    private List<Item> hitItems;
    private List<Category> mainCategories;

    @Inject
    private ItemService itemService;
    @Inject
    private CategoryService categoryService;

    @PostConstruct
    public void init(){
        hitItems = itemService.get8ActiveHitItems();
        mainCategories = categoryService.getMainActiveCategories();
        //remove first category if size more than one
        if(mainCategories.size() > 1)
            mainCategories.remove(0);

        pageComponent = pageComponentService.require("index");
        components = componentService.getAllByPageComponentAsMap(pageComponent.getId());
    }

    public List<Item> getHitItems() {
        return hitItems;
    }

    public void setHitItems(List<Item> hitItems) {
        this.hitItems = hitItems;
    }

    public List<Category> getMainCategories() {
        return mainCategories;
    }

    public void setMainCategories(List<Category> mainCategories) {
        this.mainCategories = mainCategories;
    }
}
