package com.vasax.clothes.managed.validator;

import com.vasax.clothes.entities.User;
import com.vasax.clothes.service.UserService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by root on 29.11.14.
 */
@Named
public class EmailValidator implements Validator {

    @Inject
    private UserService userService;

    @Override
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {

        String email = ""; if(value != null ) email = value.toString(); //getting email


        // Let required="true" do its job.
        if (email == null || email.isEmpty()) {
            return;
        }

        User user = userService.getCustomerByEmail(email);

        if (user != null) {
            throw new ValidatorException(new FacesMessage(
                    "Email already in use"));
        }

    }
}
