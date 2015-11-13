package com.vasax.clothes.managed.validator;

import com.vasax.clothes.entities.User;
import com.vasax.clothes.service.UserService;
import com.vasax.clothes.util.PassEncoder;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by root on 29.11.14.
 */
@Named("passwordValidator")
public class LoginPassValidator implements Validator {

    @Inject
    private UserService userService;

    @Inject
    private PassEncoder passEncoder;

    @Override
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {

        String login = ""; if(value != null ) login = value.toString();

        UIInput uiInputConfirmPassword = (UIInput) component.getAttributes()
                .get("confirmPassword");
        String pass = ""; if(uiInputConfirmPassword.getSubmittedValue() != null) pass = uiInputConfirmPassword.getSubmittedValue()
                .toString();

        // Let required="true" do its job.
        if (login == null || login.isEmpty() || pass == null
                || pass.isEmpty()) {
            return;
        }

        pass = passEncoder.encode(pass);
        User user = userService.getByLoginPassCustomer(login, pass);

        if (user == null) {
            uiInputConfirmPassword.setValid(false);
            throw new ValidatorException(new FacesMessage(
                    "Login or password incorrect"));
        }

    }
}
