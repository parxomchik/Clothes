package com.vasax.clothes.managed.admin.itemEdit;

import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.RelativeItem;
import com.vasax.clothes.service.ItemService;
import com.vasax.clothes.service.RelativeItemService;
import org.springframework.context.annotation.Scope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by vasax32 on 18.04.15.
 */
@Named
@Scope("view")
public class ItemModalSaleEdit {
    //private int selectedItemId;
    private Item item = new Item();
    //private Runnable updateParent;
    private List<String> itemsIdForSale = new ArrayList<>();


    @Inject
    private ItemService itemService;


//    public int getSelectedItemId() {
//        return selectedItemId;
//    }
//
//    public void setSelectedItemId(int selectedItemId) {
//        this.selectedItemId = selectedItemId;
//        item = itemService.getItemById(selectedItemId);
//    }
//

    public List<String> getItemsIdForSale() {
        return itemsIdForSale;
    }

    public void setItemsIdForSale(List<String> itemsIdForSale) {
        this.itemsIdForSale = itemsIdForSale;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String save() {
        for (String id : itemsIdForSale) {
            Item it = itemService.getItemById(Integer.valueOf(id));
            it.setSaleValue(item.getSaleValue());
            it.setSaleStart(item.getSaleStart());
            it.setSaleEnd(item.getSaleEnd());
            itemService.update(it);
        }
        itemsIdForSale.clear();

//        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved", "");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        if(updateParent != null)
//            updateParent.run();
        return "itemEdit.xhtml?faces-redirect=true";
    }


//    public void setUpdateParent(Runnable updateParent) {
//        this.updateParent = updateParent;
//    }
}
