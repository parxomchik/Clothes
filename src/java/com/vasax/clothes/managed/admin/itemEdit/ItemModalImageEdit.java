package com.vasax.clothes.managed.admin.itemEdit;

import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.ItemImage;
import com.vasax.clothes.managed.ConstantsBean;
import com.vasax.clothes.managed.admin.ImageDishBean;
import com.vasax.clothes.service.ImageService;
import com.vasax.clothes.service.WatermarkService;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ReorderEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vasax32 on 12.04.15.
 */
@Named
@Scope("view")
public class ItemModalImageEdit {
    private int selectedItemId;
    private Item selectedItem;
    private List<ItemImage> images;
    private Map<Integer, ItemImage> imagesMap = new HashMap<>();
    @Inject
    private ImageService imageService;
    @Inject
    private ImageDishBean imageDishBean;
    @Inject
    private WatermarkService watermarkService;
    @Inject
    private ConstantsBean constantsBean;

    public int getSelectedItemId() {
        return selectedItemId;
    }

    public void setSelectedItemId(int selectedItemId) {
        this.selectedItemId = selectedItemId;
        images = imageService.getByItem(selectedItemId);
        imagesMap.clear();
        for (ItemImage image : images) {
            imagesMap.put(image.getId(), image);
        }
//        imageService.makeResize();
    }

    public void setSelectedItemId(int selectedItemId, Item selectedItem) {
        this.selectedItem = selectedItem;
        this.setSelectedItemId(selectedItemId);
    }

    public List<ItemImage> getImages() {
        return images;
    }

    public void setImages(List<ItemImage> images) {
        this.images = images;
    }

    public void upload(FileUploadEvent event) throws IOException {
        int itemId = (Integer) event.getComponent().getAttributes().get("itemId");
        UploadedFile file = event.getFile();
        byte[] contents = IOUtils.toByteArray(file.getInputstream());

        ItemImage itemImage = new ItemImage();
        itemImage.setData(contents);
        itemImage.setSmallData(imageDishBean.scale(contents, 0, 500)); // resizing for future caching
        itemImage.setWithWatermarkData(watermarkService.addTextWatermark(constantsBean.getSiteTitle(), contents));
        itemImage.setTitle(file.getFileName());
        itemImage = imageService.add(itemImage, itemId);
        images.add(itemImage);
        imagesMap.put(itemImage.getId(), itemImage);
        selectedItem.setImages(images);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Image uploaded","File: " + file.getFileName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void removeImage(int itemId, int imageId){
        ItemImage itemImage = imagesMap.get(imageId);
        images.remove(itemImage);
        imagesMap.remove(imageId);
        imageService.remove(itemImage);
        selectedItem.setImages(images);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Image removed","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(){
        imageService.update(images);
    }

    public void onRowReorder(ReorderEvent event) {
        ItemImage getFrom = images.get(event.getFromIndex());
        ItemImage getTo = images.get(event.getToIndex());
        int temp = getFrom.getOrderId() != 0 ? getFrom.getOrderId() : getFrom.getId();
        getFrom.setOrderId(getTo.getOrderId() != 0 ? getTo.getOrderId() : getTo.getId());
        getTo.setOrderId(temp);
        //need to update view -- lazy version
        imageService.update(images);
        setSelectedItemId(this.selectedItemId);
        //add message
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item image Moved", "From: " + getFrom.getOrderId() + ", To:" + getTo.getOrderId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
