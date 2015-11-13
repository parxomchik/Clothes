package com.vasax.clothes.managed;

import com.vasax.clothes.entities.PasswordResetToken;
import com.vasax.clothes.entities.User;
import com.vasax.clothes.entities.enums.Role;
import com.vasax.clothes.service.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PreRenderViewEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Владислав on 16.05.2015.
 */
@Named
@Scope("view")
public class ChangePasswordBean {

    private String userId;
    private String resetToken;
    private String newPassword;
    private String newPassword2;

    @Inject
    private UserService userService;
    @Inject
    LoginBean loginBean;


    //Now not covered with spring security
    public void init() {
        if (loginBean.getUser() == null) {
            PasswordResetToken passToken = userService.getPasswordResetToken(resetToken);
            if (passToken != null) {
                Calendar cal = Calendar.getInstance();
                if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
                    //think how to show message
//                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Token expired", "");
//                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    redirectToPage("index.xhtml");
                } else {
                    User user = passToken.getUser();
                    if (Integer.valueOf(userId)!=user.getId()) {
                        redirectToPage("index.xhtml");
                    } else {
                        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, userService.loadUserByUsername(user.getEmail()).getAuthorities()));
                        loginBean.setUser(user);
                        loginBean.setLogin(user.getEmail());
                        loginBean.setLinked(true);
                    }
                }
            } else {
                redirectToPage("index.xhtml");
            }
        }
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void changePassword() {
        if (!("".equals(newPassword))) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.changePassword(user, newPassword);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Пароль сохранен", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    private void redirectToPage(String page) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
