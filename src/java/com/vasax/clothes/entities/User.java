package com.vasax.clothes.entities;

import com.vasax.clothes.entities.enums.Role;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 07.10.14.
 */
@Entity
@Table(name = "userX")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String phone;
    private String country;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private Integer postIndex;
    private String pass;
    private Timestamp regDate;
    private boolean active;
    private int discount;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Order> orders;

    public User(){
        regDate = new Timestamp(new Date().getTime());
        active = true;
    }

    public User(String name, String email, String phone, String street, String pass, Role role, String country, String city, Integer postIndex) {
        this();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.pass = pass;
        this.role = role;
        this.country = country;
        this.city = city;
        this.postIndex = postIndex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String address) {
        this.street = address;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Timestamp getRegDate() {
        return regDate;
    }

    public void setRegDate(Timestamp regDate) {
        this.regDate = regDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public void setPostIndex(Integer index) {
        this.postIndex = index;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (active != user.active) return false;
        if (discount != user.discount) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (country != null ? !country.equals(user.country) : user.country != null) return false;
        if (city != null ? !city.equals(user.city) : user.city != null) return false;
        if (street != null ? !street.equals(user.street) : user.street != null) return false;
        if (house != null ? !house.equals(user.house) : user.house != null) return false;
        if (apartment != null ? !apartment.equals(user.apartment) : user.apartment != null) return false;
        if (postIndex != null ? !postIndex.equals(user.postIndex) : user.postIndex != null) return false;
        if (pass != null ? !pass.equals(user.pass) : user.pass != null) return false;
        if (regDate != null ? !regDate.equals(user.regDate) : user.regDate != null) return false;
        if (role != user.role) return false;
        return !(orders != null ? !orders.equals(user.orders) : user.orders != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (house != null ? house.hashCode() : 0);
        result = 31 * result + (apartment != null ? apartment.hashCode() : 0);
        result = 31 * result + (postIndex != null ? postIndex.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        result = 31 * result + (regDate != null ? regDate.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + discount;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (orders != null ? orders.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", apartment='" + apartment + '\'' +
                ", postIndex=" + postIndex +
                ", pass='" + pass + '\'' +
                ", regDate=" + regDate +
                ", active=" + active +
                ", discount=" + discount +
                ", role=" + role +
                ", orders=" + orders +
                '}';
    }
}
