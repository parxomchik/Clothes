package com.vasax.clothes.service;

import com.vasax.clothes.dao.OrderDao;
import com.vasax.clothes.entities.Order;
import com.vasax.clothes.entities.enums.Status;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by oper4 on 03.11.2014.
 */
@Named
public class OrderService {

    @Inject
    private
    OrderDao orderDao;

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Transactional
    public Order getOrderById(int id){
        return orderDao.selectById(id);
    }

    @Transactional
    public List<Order> getAllOrders(){
        return orderDao.selectAll();
    }

    @Transactional
    public Order update(Order order){
        return orderDao.update(order);
    }

    @Transactional
    public Order add(Order order){
        return orderDao.insert(order);
    }

    public List<Order> getToDeliveryOrders() {
        return orderDao.selectOrdersByStatusToDeliverySortByDateToDelivery(Status.toDelivery);
    }

    @Transactional
    public List<Order> getOrderedOrdersOrderedByDateAdd() {
        List<Order> orders = orderDao.selectOrdersByStatusOrderedBuyDateAdd(Status.ordered);
        for (Order o : orders)
            o.getOrderItems().size();
        return orders;
    }

    @Transactional
    public List<Order> getPayedOrOrderedOrdersOrderedByDateAdd() {
        List<Order> orders = orderDao.selectPayedOROrdersByStatusOrderedBuyDateAdd();
        for (Order o : orders)
            o.getOrderItems().size();
        return orders;
    }

    @Transactional
    public List<Order> getOrderedHistoryOfOrdersOrderedByDateDone() {
        List<Order> orders = orderDao.selectHistoryOfOrdersByStatusDoneOrderedByDateDone(Status.done);
        for (Order o : orders)
            o.getOrderItems().size();
        return orders;
    }

    public Order getByPaymentId(String paymentId) {
        return orderDao.selectByPaymentId(paymentId);
    }
}
