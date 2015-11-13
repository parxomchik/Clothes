package com.vasax.clothes.managed.convertor;

import com.vasax.clothes.entities.Category;
import com.vasax.clothes.entities.Item;
import com.vasax.clothes.service.ItemService;
import org.primefaces.component.autocomplete.AutoComplete;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16.11.14.
 */
@Named
@Scope("view")
public class ItemConverter implements Converter {

    private Map<Integer, Item> items = new HashMap<>();

    @Inject
    private ItemService itemService;

    @PostConstruct
    public void init(){
        List<Item> itemList = itemService.getAllItems();
        for (Item item : itemList)
            items.put(item.getId(), item);
    }

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        submittedValue = (String)((AutoComplete) component).getSubmittedValue();
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);

                Item item = items.get(number);
                return item;

            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));
            }
        }
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            Item item = (Item) value;
            return String.valueOf(item.getId());
        }
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    public void setItems(Map<Integer, Item> items) {
        this.items = items;
    }
}
