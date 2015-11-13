package com.vasax.clothes.managed.admin;

import com.vasax.clothes.entities.User;
import com.vasax.clothes.managed.LoginBean;
import com.vasax.clothes.service.UserService;
import com.vasax.clothes.util.PassEncoder;
import org.springframework.context.annotation.Scope;

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
@Scope("view")
public class CurrentPassValidator implements Validator {

    @Inject
    private LoginBean loginBean;

    @Inject
    private PassEncoder passEncoder;

    @Override
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {

        String pass = ""; if(value != null ) pass = value.toString(); //getting pass


        // Let required="true" do its job.
        if (pass == null || pass.isEmpty()) {
            return;
        }

        User user = loginBean.getUserSpring();

        if (!user.getPass().equals(passEncoder.encode(pass))) {
            throw new ValidatorException(new FacesMessage(
                    "Invalid current password"));
        }

    }
}
