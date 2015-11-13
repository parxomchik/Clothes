package com.vasax.clothes.managed;

import com.vasax.clothes.entities.User;
import com.vasax.clothes.util.PassEncoder;
import com.vasax.clothes.entities.enums.Role;
import com.vasax.clothes.service.UserService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

/**
 * Created by root on 16.12.14.
 */
@Named
public class RegisterBean {

    @Inject
    private UserService userService;

    @Inject
    private PassEncoder passEncoder;

    private String email;
    private String pass;
    private String pass2;
    private String name;
    private String phone;
    private String address;
    private String country;
    private String city;
    private Integer postIndex;

    public String registerAction() throws IOException {
        String encodedPass = passEncoder.encode(pass);
        Role role = Role.valueOf("CUSTOMER");
        User user = new User(name, email, phone, address, encodedPass, role, country, city, postIndex);
        user = userService.save(user);
        if (user != null) {
            clearFields();
            //FacesContext.getCurrentInstance().getExternalContext().redirect("");
        }

        return "";
    }

    private void clearFields(){
        email = "";
        pass = "";
        pass2 = "";
        name = "";
        phone = "";
        address = "";
        country = "";
        city = "";
        postIndex = null;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPostIndex() {
        return postIndex;
    }

    public void setPostIndex(Integer postIndex) {
        this.postIndex = postIndex;
    }
}
