package com.vasax.clothes.managed.admin.itemEdit;

import com.vasax.clothes.entities.Category;
import com.vasax.clothes.entities.Item;
import com.vasax.clothes.managed.ConstantsBean;
import com.vasax.clothes.service.CategoryService;
import com.vasax.clothes.service.ItemService;
import com.vasax.clothes.service.RelativeItemService;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.ReorderEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * Created by root on 15.11.14.
 */
@Named
@Scope("view")
public class ItemEditBean {

    @Inject
    private ItemService itemService;
    @Inject
    private CategoryService categoryService;

    private List<Item> items;
    private List<Item> filteredItems;
    private List<String> itemsIdForDuplicate;
    private List<Item> newItems;

    private Map<Integer, Item> itemsMap = new HashMap<>();
    private UploadedFile file;

    private Category currentCategory;
    private List<Category> allCategories;

    @Inject
    private ItemModalFilterEdit itemModalFilterEdit;
    @Inject
    private ItemModalImageEdit itemModalImageEdit;
    @Inject
    private ItemModalRelativeEdit itemModalRelativeEdit;
    @Inject
    private ItemModalSaleEdit itemModalSaleEdit;
    @Inject
    private ConstantsBean constantsBean;
    @Inject
    private RelativeItemService relativeItemService;

    private boolean saved = true;
    private boolean copyModeActive = false;
    private int cloneCount=1;
    private int addCount=1;
    private int maxId = -1;


    @PostConstruct
    public void init() {

        allCategories = categoryService.getAllSubCategories();
        if(allCategories.size() > 0)
            currentCategory = allCategories.get(0);

        initItems();
    }

    public void initItems(){
        if(currentCategory != null){
            items = itemService.getByCategoryOrderByActiveAndDateModified(currentCategory.getId());
        } else{
            items = itemService.getAllItemsOrderByActiveAndDateModified();
        }


        for (Item item : items)
            itemsMap.put(item.getId(), item);

        //System.out.println("init");
        itemsIdForDuplicate = new ArrayList<>();
        newItems = new ArrayList<>();
    }


    public void currentCategoryChanged(){
        initItems();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getCloneCount() {
        return cloneCount;
    }

    public void setCloneCount(int cloneCount) {
        this.cloneCount = cloneCount;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<Item> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<Item> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public boolean isCopyModeActive() {
        return copyModeActive;
    }

    public void setCopyModeActive(boolean isCopyModeActive) {
        this.copyModeActive = isCopyModeActive;
    }

    public List<String> getItemsIdForDuplicate() {
        return itemsIdForDuplicate;
    }

    public void setItemsIdForDuplicate(List<String> itemsIdForDuplicate) {
        this.itemsIdForDuplicate = itemsIdForDuplicate;
    }

    public List<Category> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<Category> allCategories) {
        this.allCategories = allCategories;
    }

    public void onRowReorder(ReorderEvent event) {
        Item getFrom = items.get(event.getFromIndex());
        Item getTo = items.get(event.getToIndex());
        int temp = getFrom.getOrderId();
        getFrom.setOrderId(getTo.getOrderId());
        getTo.setOrderId(temp);
        Collections.swap(items, event.getFromIndex(), event.getToIndex());


        //add message
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Moved", "From: " + getFrom.getId() + ", To:" + getTo.getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String save() {
        try {
            if (filteredItems == null || items.size() == filteredItems.size()) {
                items.removeAll(newItems);
                List<Item> insertedItems = itemService.insertItems(newItems);

                items = itemService.update(items);
                Collections.reverse(insertedItems);
                items.addAll(0,insertedItems);
                newItems.clear();
                itemsMap.clear();
                for (Item item : items) {
                    itemsMap.put(item.getId(), item);
                }
            } else {
                if (filteredItems != null) {
                    itemService.update(filteredItems);
                }
                init();
            }
            saved = true;
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            addCount=1;
            maxId = -1;
        } catch (Exception e){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Saving error", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }

    public String reload() {
        itemsMap.clear();
        initItems();
        filteredItems = null;
        return "";
    }

    public String addNew() {
        Item item = new Item();
        item.setId(getRandomId());
        item.setFirm(constantsBean.getProjectName());
        item.setPackSize(" S, M, L, XL, XXL");
        item.setActive(true);
        newItems.add(item);
        items.add(0, item);
        itemsMap.put(item.getId(), item);
        saved = false;
        return "";
    }

    public void selectItem(int itemId) {
        if (!saved)
            save();
        itemModalFilterEdit.setSelectedItemId(itemId);
    }

    public void selectItemForImageEdit(int itemId) {
//        Item itemForGet = itemsMap.get(itemId);
//        int indexOfItemForGet = items.indexOf(itemForGet);
//        items.remove(itemForGet);
//        itemForGet = itemService.update(itemForGet);
//        items.add(indexOfItemForGet, itemForGet);
//        itemsMap.remove(itemId);
//        itemsMap.put(itemForGet.getId(), itemForGet);
        if (!saved)
            save();
        Item itemForGet = itemsMap.get(itemId);
        itemModalImageEdit.setSelectedItemId(itemForGet.getId(), itemForGet);
    }

    public void selectItemForRelative(int itemId) {
        if (!saved)
            save();
        itemModalRelativeEdit.setSelectedItemId(itemId);
    }

//    public void selectItemForSaleR(final int itemId) {
//        if (!saved)
//            save();
//        itemModalSaleEdit.setSelectedItemId(itemId);
//        itemModalSaleEdit.setUpdateParent(new Runnable() {
//            @Override
//            public void run() {
//                Item newItem = itemService.getItemById(itemId);
//                replaceIn(items, newItem);
//                replaceIn(filteredItems, newItem);
//                replaceIn(itemsMap, newItem);
//            }
//        });
//    }

    public String cloneItems() {
        if (itemsIdForDuplicate.size()>0) {
            for (String itemId : itemsIdForDuplicate) {
                Item prototypeItem = itemService.getItemById(Integer.valueOf(itemId));
                for (int i=0; i<cloneCount;++i) {
                    cloneItem(prototypeItem);
                }
            }
            itemsIdForDuplicate.clear();
            save();
        }
        return "itemEdit.xhtml?faces-redirect=true";
    }

    private void cloneItem(Item prototypeItem) {
        Item item = itemService.cloneItem(prototypeItem);
        relativeItemService.copyRelativeItems(prototypeItem.getId(), item.getId());
        items.add(0, item);
        itemsMap.put(item.getId(), item);
        saved = false;
//        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item \"" + item.getTitle() + "\" duplicated", "");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private int findIn(List<Item> items, int id){
        if(items == null)
            return -1;
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getId() == id)
                return i;
        }
        return -1;
    }

    private void replaceIn(List<Item> collectionInReplace, Item replaceBy){
        int index = findIn(collectionInReplace, replaceBy.getId());
        if(index != -1){
            //need to replace
            collectionInReplace.remove(index);
            collectionInReplace.add(index, replaceBy);
        }
    }

    private void replaceIn(Map<Integer, Item> mapInReplace, Item replaceBy){
        if(mapInReplace.containsKey(replaceBy.getId()))
            mapInReplace.put(replaceBy.getId(), replaceBy);
    }

    private int getRandomId() {
//        Random r = new Random();
//        int x = 0;
//        while (true) {
//            x = r.nextInt(items.size() + 100);
//            boolean notIn = true;
//            for (Item item : items)
//                if (item.getId() == x)
//                    notIn = false;
//            if (notIn)
//                break;
//
//        }

        if(maxId == -1) {
            maxId = itemService.getMaxId();
        }
        int x = maxId + addCount;
        addCount++;
        return x;
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }
}
