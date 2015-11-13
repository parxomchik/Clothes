package com.vasax.clothes.entities;

import com.vasax.clothes.entities.enums.DeliveryType;
import com.vasax.clothes.entities.enums.PaymentType;
import com.vasax.clothes.entities.enums.Status;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 07.10.14.
 */
@Entity
@Table(name = "orderX")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Timestamp dateAdd;
    private Timestamp dateDone;
    private Timestamp dateDelivered;
    private double total;
    private String paymentId;
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;
    private String serviceTitle; //название службы
    private String addressOrStorageNum;//адресс/номер склада
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToMany
    //@LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "orderItem",
            joinColumns = @JoinColumn(name = "idOrder"),
            inverseJoinColumns = @JoinColumn(name = "idItem"))
    private List<Item> items;

    @OneToMany
    @JoinColumn(name="idOrder")
    //@LazyCollection(LazyCollectionOption.FALSE)
    private List<OrderItem> orderItems;

    public Order(){
        dateAdd = new Timestamp(new Date().getTime());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Timestamp dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Timestamp getDateDone() {
        return dateDone;
    }

    public void setDateDone(Timestamp dateDone) {
        this.dateDone = dateDone;
    }

    public Timestamp getDateDelivered() {
        return dateDelivered;
    }

    public void setDateDelivered(Timestamp dateDelivered) {
        this.dateDelivered = dateDelivered;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceType) {
        this.serviceTitle = serviceType;
    }

    public String getAddressOrStorageNum() {
        return addressOrStorageNum;
    }

    public void setAddressOrStorageNum(String addressOrStorageNum) {
        this.addressOrStorageNum = addressOrStorageNum;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order that = (Order) o;

        if (id != that.id) return false;
        if (status != that.status) return false;
//        if (dateAdd != null ? !dateAdd.equals(that.dateAdd) : that.dateAdd != null) return false;
//        if (dateDelivered != null ? !dateDelivered.equals(that.dateDelivered) : that.dateDelivered != null)
//            return false;
//        if (dateDone != null ? !dateDone.equals(that.dateDone) : that.dateDone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dateAdd != null ? dateAdd.hashCode() : 0);
        result = 31 * result + (dateDone != null ? dateDone.hashCode() : 0);
        result = 31 * result + (dateDelivered != null ? dateDelivered.hashCode() : 0);
        result = 31 * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", dateAdd=" + dateAdd +
                ", dateDone=" + dateDone +
                ", dateDelivered=" + dateDelivered +
                ", status=" + status +
                '}';
    }
}
