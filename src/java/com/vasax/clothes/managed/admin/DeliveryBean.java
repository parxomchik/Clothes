package com.vasax.clothes.managed.admin;

import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.enums.Status;
import com.vasax.clothes.service.OrderService;
import com.vasax.clothes.entities.Order;
import com.vasax.clothes.service.ItemService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by root on 15.11.14.
 */
@Named
@Scope("view")
public class DeliveryBean {

    @Inject
    private OrderService orderService;

    @Inject
    private ItemService itemService;

    private Set<Order> orders;

    @PostConstruct
    public void init() {
        orders = new LinkedHashSet<>(orderService.getToDeliveryOrders());
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public String reload(){
        init();
        return "";
    }

    public String markAsDone(int id){
        Order order = orderService.getOrderById(id);
        orders.remove(order);
        order.setStatus(Status.done);
        order.setDateDone(new Timestamp(new Date().getTime()));
        orderService.update(order);
        return "";
    }


    public List<Item> getDishesByOrder(int orderId){
        return itemService.getDishesByOrderId(orderId);
    }
}
