package com.vasax.clothes.managed.admin;

import com.vasax.clothes.entities.User;
import com.vasax.clothes.entities.enums.Role;
import com.vasax.clothes.service.UserService;
import com.vasax.clothes.util.PassEncoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 19.12.14.
 */
@Named
@Scope("view")
public class CustomerEditBean {
    @Inject
    private UserService userService;
    @Inject
    private PassEncoder passEncoder;

    private List<User> customers;
    private List<User> added;

    @PostConstruct
    public void init(){
        customers = userService.getCustomers();
        added = new ArrayList<>();
    }

    public String save(){

//        for (User user : added) {
//            user.setPass(passEncoder.encode(user.getPass()));
//        }
//        added.clear();
//        for (User user : customers) {
//            if(user.getPass().length() < 64)
//                user.setPass(passEncoder.encode(user.getPass()));
//        }
        userService.updateAll(customers);

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;
    }

    public String addNew(){
        User user = new User();
        user.setId(getRandomId());
        user.setRole(Role.CUSTOMER);
        customers.add(user);
        added.add(user);
        return "";
    }

    public String reload(){
        init();
        return "";
    }

    public List<User> getCustomers() {
        return customers;
    }

    public void setCustomers(List<User> customers) {
        this.customers = customers;
    }

    public String replacePassWithDots(String pass){
        String padStr = "&#9679;";
        return StringUtils.leftPad("", pass.length() * padStr.length(), padStr);
    }

    private int getRandomId(){
        int max = 0;
        for(User user : customers)
            if(user.getId() > max)
                max = user.getId();

        return max+1;
    }
}
