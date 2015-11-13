package com.vasax.clothes.managed.admin.itemEdit;

import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.RelativeItem;
import com.vasax.clothes.service.ItemService;
import com.vasax.clothes.service.RelativeItemService;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by vasax32 on 18.04.15.
 */
@Named
@Scope("view")
public class ItemModalRelativeEdit {
    private int selectedItemId;
    private List<Item> items;
    private Set<Integer> relativeIds;
    private List<Item> filteredItems;

    @Inject
    private ItemService itemService;

    @Inject
    private RelativeItemService relativeItemService;

    public int getSelectedItemId() {
        return selectedItemId;
    }

    public void setSelectedItemId(int selectedItemId) {
        this.selectedItemId = selectedItemId;
        items = itemService.getAllActiveItemsWithouId(selectedItemId);
        relativeIds = new HashSet<>(relativeItemService.getActiveRelativeIdsByItem(selectedItemId));
    }

    public void addRelative(int relativeItemId){
        RelativeItem relativeItem = new RelativeItem();

        Item selectedItem = itemService.getItemById(selectedItemId);
        Item relativeItemItem = itemService.getItemById(relativeItemId);
        relativeItem.setItem(selectedItem);
        relativeItem.setRelative(relativeItemItem);

        RelativeItem added = relativeItemService.add(relativeItem);
        relativeIds.add(added.getRelative().getId());
    }

    public void removeRelative(int relativeItemId){
        relativeItemService.removeByIds(selectedItemId, relativeItemId);
        relativeIds.remove(relativeItemId);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Set<Integer> getRelativeIds() {
        return relativeIds;
    }

    public void setRelativeIds(Set<Integer> relativeIds) {
        this.relativeIds = relativeIds;
    }

    public List<Item> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<Item> filteredItems) {
        this.filteredItems = filteredItems;
    }
}
