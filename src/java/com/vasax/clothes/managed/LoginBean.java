package com.vasax.clothes.managed;

import com.vasax.clothes.entities.enums.CurrencyType;
import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.User;
import com.vasax.clothes.service.EmailService;
import com.vasax.clothes.util.CustomUserDetailsService;
import com.vasax.clothes.util.PassEncoder;
import com.vasax.clothes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by vasax32 on 24.11.14.
 */
@Named
@Scope("session")
public class LoginBean implements Serializable {
    private String login;
    private String pass;
    private boolean linked;
    private User user;
    private Set<Item> viewedItems = new HashSet<>();
    private CurrencyType currencyType = CurrencyType.grn;
    private String forgotEmail;

    @Inject
    private UserService userService;
    @Inject
    EmailService emailService;
    @Inject
    private PassEncoder passEncoder;


    public String loginAction() throws IOException {
        pass = passEncoder.encode(pass);
        user = userService.getByLoginPassCustomer(login, pass);
        if (user != null) {
            linked = true;
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, userService.loadUserByUsername(login).getAuthorities()));
            //reload page when login and pass are correct
            FacesContext.getCurrentInstance().getExternalContext().redirect("");
        }
        return "";
    }

    public void currencyChanged() throws IOException {
        switch (currencyType) {
            case grn:
                currencyType = CurrencyType.usd;
                break;
            case usd:
                currencyType = CurrencyType.grn;
                break;
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("");
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getViewedItemsAsList() {
        return new ArrayList<>(viewedItems);
    }

    public Set<Item> getViewedItems() {
        return viewedItems;
    }

    public void setViewedItems(Set<Item> viewedItems) {
        this.viewedItems = viewedItems;
    }


    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public String getForgotEmail() {
        return forgotEmail;
    }

    public void setForgotEmail(String forgotEmail) {
        this.forgotEmail = forgotEmail;
    }

    public String logoutAction() throws IOException {
        user = null;
        linked = false;
        login = "";
        pass = "";
        return "index.xhtml?faces-redirect=true";
    }

    public String getUserSpringUserLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public User getUserSpring() {
        String login = getUserSpringUserLogin();
        user = userService.getByEmail(login);
        return user;
    }

    public String resetPassword() {
        User user = userService.getCustomerByEmail(forgotEmail);
        if (user == null) {
            //message
        }
        String resetToken = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, resetToken);
        HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
        String appUrl =
                "http://" + origRequest.getServerName() + ":" + origRequest.getServerPort() + origRequest.getContextPath();
        String resetUrl = appUrl + "/app/changePassword.xhtml?id=" + user.getId() + "&token=" + resetToken;
        emailService.sendResetPasswordEmail(user.getEmail(), resetUrl);

        return "index.xhtml";
    }

}
