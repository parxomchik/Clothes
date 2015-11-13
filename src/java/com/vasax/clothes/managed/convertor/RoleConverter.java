package com.vasax.clothes.managed.convertor;

import com.vasax.clothes.entities.enums.Role;
import org.springframework.context.annotation.Scope;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;

/**
 * Created by root on 16.11.14.
 */
@Named
@Scope("view")
//@FacesConverter(forClass = Category.class,value="categoryConverter")
public class RoleConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);

                Role role = Role.values()[number];
                return role;

            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));
            }
        }
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            Role role = (Role)value;
            return String.valueOf(role.ordinal());
        }
    }
}
