package com.vasax.clothes.managed.admin;

import com.vasax.clothes.entities.Order;
import com.vasax.clothes.entities.enums.Status;
import com.vasax.clothes.service.EmailService;
import com.vasax.clothes.service.OrderService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Created by root on 15.11.14.
 */
@Named
@Scope("view")
public class OrderBean {

    @Inject
    private OrderService orderService;
    @Inject
    private EmailService emailService;

    private LinkedHashSet<Order> orders;
    private Map<Integer, Order> orderMap;

    @PostConstruct
    public void init() {
        orders = new LinkedHashSet<>(orderService.getPayedOrOrderedOrdersOrderedByDateAdd());
        orderMap = new HashMap<>();
        for (Order order : orders) {
            orderMap.put(order.getId(), order);
        }
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
        init();
        return "";
    }

    public String markAsOrdered(int orderId){
        Order order = orderService.getOrderById(orderId);
        order.setStatus(Status.ordered);
        orderService.update(order);
        emailService.sendUserNotificationAboutOrderStatusChangedToOrdered(order.getId(), getApplicationUri());
        orderMap.get(order.getId()).setStatus(Status.ordered); //update ui
        return "";
    }

    public String getApplicationUri() {
        try {
            FacesContext ctxt = FacesContext.getCurrentInstance();
            ExternalContext ext = ctxt.getExternalContext();
            URI uri = new URI(ext.getRequestScheme(),
                    null, ext.getRequestServerName(), ext.getRequestServerPort(),
                    ext.getRequestContextPath(), null, null);
            return uri.toASCIIString();
        } catch (URISyntaxException e) {
            throw new FacesException(e);
        }
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
