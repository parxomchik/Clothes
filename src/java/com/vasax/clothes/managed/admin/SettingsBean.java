package com.vasax.clothes.managed.admin;

import com.vasax.clothes.entities.User;
import com.vasax.clothes.managed.LoginBean;
import com.vasax.clothes.service.UserService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by vasax32 on 27.03.15.
 */
@Named
@Scope("view")
public class SettingsBean {

    @Inject
    private LoginBean loginBean;

    @Inject
    private UserService userService;

    private String currentPass;
    private String newPass;
    private String confirmNewPass;
    private User user;

    @PostConstruct
    public void init(){
        user = loginBean.getUserSpring();
    }

    public String save(){
        user.setPass(newPass);
        userService.update(user);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return "";
    }

    public String getCurrentPass() {
        return currentPass;
    }

    public void setCurrentPass(String currentPass) {
        this.currentPass = currentPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getConfirmNewPass() {
        return confirmNewPass;
    }

    public void setConfirmNewPass(String confirmNewPass) {
        this.confirmNewPass = confirmNewPass;
    }
}
