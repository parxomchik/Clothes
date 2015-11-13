package com.vasax.clothes.managed.admin.itemEdit;

import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.ItemAttributeProperty;
import com.vasax.clothes.entities.Property;
import com.vasax.clothes.service.ItemAttributePropertyService;
import com.vasax.clothes.service.PropertyService;
import org.springframework.context.annotation.Scope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasax32 on 11.04.15.
 */
@Named
@Scope("view")
public class ItemModalFilterEdit {
    private int selectedItemId;
    private List<ItemAttributeProperty> iaps;

    @Inject
    private ItemAttributePropertyService iapService;
    @Inject
    private PropertyService propertyService;


    public int getSelectedItem() {
        return selectedItemId;
    }

    public void setSelectedItemId(int selectedItemId) {
        this.selectedItemId = selectedItemId;
        iaps = iapService.getWithNullsByItemId(selectedItemId);
    }

    public List<ItemAttributeProperty> getIaps() {
        return iaps;
    }

    public void setIaps(List<ItemAttributeProperty> iaps) {
        this.iaps = iaps;
    }

    public List<Property> getAvailableProperties(int attributeId) {
        List<Property> propertiesByAttribute = propertyService.getPropertiesByAttribute(attributeId);
        List<Property> ret = new ArrayList<>();
        Property nullProp = new Property();
        nullProp.setId(-1);
        nullProp.setTitle("NULL");
        ret.add(nullProp);
        ret.addAll(propertiesByAttribute);
        return ret;
    }

    public String saveIaps() {
        iapService.updateProperty(iaps);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;
    }
}
