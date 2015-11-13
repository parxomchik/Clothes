package com.vasax.clothes.managed.admin;

import com.vasax.clothes.entities.Order;
import com.vasax.clothes.entities.enums.Status;
import com.vasax.clothes.service.OrderService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashSet;

/**
 * Created by root on 15.11.14.
 */
@Named
@Scope("view")
public class OrderHistoryBean {

    @Inject
    private OrderService orderService;

    private LinkedHashSet<Order> orders;

    @PostConstruct
    public void init() {
        orders = new LinkedHashSet<>(orderService.getOrderedHistoryOfOrdersOrderedByDateDone());
    }

    public LinkedHashSet<Order> getOrders() {
        return orders;
    }

    public void setOrders(LinkedHashSet<Order> orders) {
        this.orders = orders;
    }

    public String reload(){
        init();
        return "";
    }

    public String markAsToDelivery(int orderId){
        Order order = orderService.getOrderById(orderId);
        orders.remove(order);
        order.setStatus(Status.toDelivery);
        order.setDateDelivered(new Timestamp(new Date().getTime()));
        orderService.update(order);
        return "";
    }
//
//    public String markAsDone(int id) {
//        OrderDish orderDish = orderDishService.getOrderDishById(id);
//        orders.remove(orderDish);
//        Status status = Status.done;
//        orderDish.setStatus(status);
//        orderDish.setDateDone(new Timestamp(new Date().getTime()));
//        orderDishService.update(orderDish);
//        //now we need to check if order is done
//        Order order = orderDish.getOrder();
//        List<OrderDish> dishesToCheck = orderDishService.getOrderItemByOrderId(order.getId());
//        boolean allDone = true;
//        for(OrderDish od : dishesToCheck){
//            if(!od.getStatus().equals(Status.done))
//                allDone = false;
//        }
//        order.setStatus(status);
//        orderService.update(order);
//        return "";
//    }
}
