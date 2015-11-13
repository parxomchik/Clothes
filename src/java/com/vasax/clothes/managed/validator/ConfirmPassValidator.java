package com.vasax.clothes.managed.validator;

import com.vasax.clothes.service.UserService;
import org.primefaces.component.password.Password;

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
public class ConfirmPassValidator implements Validator {

    @Inject
    private UserService userService;

    @Override
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {

        String pass1 = ""; if(value != null ) pass1 = value.toString();

        Password uiInputConfirmPassword = (Password) component.getAttributes()
                .get("password");
        String pass2 = ""; if(uiInputConfirmPassword.getSubmittedValue() != null) pass2 = uiInputConfirmPassword.getSubmittedValue()
                .toString();

        // Let required="true" do its job.
        if (pass2 == null || pass2.isEmpty() || pass2 == null
                || pass2.isEmpty()) {
            return;
        }

        if (!pass1.equals(pass2)) {
            uiInputConfirmPassword.setValid(false);
            throw new ValidatorException(new FacesMessage(
                    "Confirm password is incorrect"));
        }

    }
}
