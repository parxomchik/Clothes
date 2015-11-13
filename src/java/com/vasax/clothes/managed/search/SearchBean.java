package com.vasax.clothes.managed.search;

import com.vasax.clothes.entities.Item;
import com.vasax.clothes.managed.ConstantsBean;
import com.vasax.clothes.service.ItemService;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasax32 on 28.03.15.
 */
@Named
@Scope("view")
public class SearchBean {

    @Inject
    private ItemService itemService;
    @Inject
    private ConstantsBean constantsBean;

    private String inputQuery;
    private List<Item> results;
    private Item item;
    private int selectedId;

    @PostConstruct
    public void init(){
        results = new ArrayList<>();
    }

    public List<Item> complete(String query){
        this.inputQuery = query;
        return itemService.search(query);
    }

    public void makeQuery(){
        if (inputQuery != null) {
            results.clear();
            results.addAll(itemService.search(inputQuery));
        }
    }

    public String getInputQuery() {
        return inputQuery;
    }

    public void setInputQuery(String inputQuery) {
        this.inputQuery = inputQuery;
    }

    public List<Item> getResults() {
        return results;
    }

    public void setResults(List<Item> results) {
        this.results = results;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }

//    public void handleSelect(AjaxBehaviorEvent event)  {
    public void onItemSelect(SelectEvent event) throws IOException {
        Item item = (Item) event.getObject();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/" + constantsBean.getPrefix() + "/item.xhtml?id=" + item.getId());
    }
}
