package com.vasax.clothes.managed.convertor;

import com.vasax.clothes.entities.Category;
import com.vasax.clothes.entities.Property;
import com.vasax.clothes.service.CategoryService;
import com.vasax.clothes.service.PropertyService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16.11.14.
 */
@Named
//@Scope("view")
//@FacesConverter(forClass = Category.class,value="categoryConverter")
public class PropertyAttrConverter implements Converter {

    @Inject
    private PropertyService propertyService;

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);

                Property property = propertyService.getById(number);
                return property;

            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));
            }
        }
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            Property property = (Property) value;
            return String.valueOf(property.getId());
        }
    }
}
