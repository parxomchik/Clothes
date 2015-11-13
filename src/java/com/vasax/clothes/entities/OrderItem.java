package com.vasax.clothes.entities;

import com.vasax.clothes.entities.enums.Status;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by root on 07.10.14.
 */
@Entity
@Table(name = "orderItem")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Timestamp dateDone;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "idItem")
    private Item item;


    @ManyToOne
    @JoinColumn(name = "idOrder")
    private Order order;

    private double price; //price per item, to calculate total price: price * count
    private int count; //count of ordered items

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateDone() {
        return dateDone;
    }

    public void setDateDone(Timestamp dateDone) {
        this.dateDone = dateDone;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem that = (OrderItem) o;

        if (id != that.id) return false;
        if (dateDone != null ? !dateDone.equals(that.dateDone) : that.dateDone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dateDone != null ? dateDone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderDish{" +
                "id=" + id +
                ", dateDone=" + dateDone +
                '}';
    }
}
