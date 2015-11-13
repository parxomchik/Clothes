package com.vasax.clothes.managed.admin.categoryEdit;

import com.vasax.clothes.entities.Attribute;
import com.vasax.clothes.entities.Category;
import com.vasax.clothes.entities.Property;
import com.vasax.clothes.service.AttributeService;
import com.vasax.clothes.service.PropertyService;
import org.springframework.context.annotation.Scope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasax32 on 10.04.15.
 */
@Named
@Scope("view")
public class CategoryModalEditBean {
    @Inject
    private AttributeService attributeService;
    @Inject
    private PropertyService propertyService;

    private Category selectedCategory;
    private List<Attribute> attributes;
    private List<Property> properties;
    private int selectedAttributeId = -1;

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
        attributes = attributeService.getByCategoryId(selectedCategory.getId());
        if(attributes.size() > 0)
            onRowSelect(0);
        else properties = new ArrayList<>();
    }

    public void onRowSelect(int rowIndex) {
        Attribute attribute = attributes.get(rowIndex);
        if(attribute.getId() == 0) {
            attribute = attributeService.update(attribute);
            attributes.remove(rowIndex);
            attributes.add(rowIndex, attribute);
        }
        selectedAttributeId = attribute.getId();
        properties = propertyService.getPropertiesByAttribute(selectedAttributeId);
    }

    public void addNewAttribute() {
        Attribute e = new Attribute();
        e.setCategory(selectedCategory);
        attributes.add(e);
    }

    public void removeAttribute(int id) {
        Attribute found = null;
        for (Attribute attribute : attributes) {
            if (attribute.getId() == id) {
                found = attribute;
                break;
            }
        }
        attributes.remove(found);

        //now we need to remove it from db
        attributeService.removeById(id);
    }

    public void saveAttributes(){
        attributes = attributeService.update(attributes);
        if(selectedAttributeId == -1){
            if(attributes.size() > 0)
                selectedAttributeId = attributes.get(0).getId();
        }
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved","");
        FacesContext.getCurrentInstance().addMessage("msgs", msg);
    }

    public void addNewProperty() {
        if(selectedAttributeId == -1){
            //need to save attribute
            saveAttributes();
        }
        Property property = new Property();
        property.setAttribute(attributeService.getById(selectedAttributeId));
        properties.add(property);
    }

    public void removeProperty(int id) {
        Property found = null;
        for (Property property : properties) {
            if (property.getId() == id) {
                found = property;
                break;
            }
        }
        properties.remove(found);

        //now we need to remove it from db
        propertyService.removeById(id);
    }

    public void saveProperties(){
        properties = propertyService.update(properties);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved","");
        FacesContext.getCurrentInstance().addMessage("msgs", msg);
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public int getSelectedAttributeId() {
        return selectedAttributeId;
    }

    public void setSelectedAttributeId(int selectedAttributeId) {
        this.selectedAttributeId = selectedAttributeId;
    }

    public void saveAll() {
        saveAttributes();
        saveProperties();
    }
}
