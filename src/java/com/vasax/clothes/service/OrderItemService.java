package com.vasax.clothes.service;

import com.vasax.clothes.entities.OrderItem;
import com.vasax.clothes.dao.OrderItemDao;
import com.vasax.clothes.entities.enums.Status;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by oper4 on 03.11.2014.
 */
@Named
public class OrderItemService {

    @Inject
    OrderItemDao orderItemDao;

    @Transactional
    public OrderItem getOrderDishById(int id){
        return orderItemDao.selectById(id);
    }

    //just added in front
    public List<OrderItem> getOrderedAndCookingDishes(){
        List<OrderItem> orderedDishes = orderItemDao.selectDishesByStatus(Status.ordered);
        List<OrderItem> cookingDishes = orderItemDao.selectDishesByStatus(Status.cooking);
        cookingDishes.addAll(orderedDishes);
        return cookingDishes;
    }

    @Transactional
    public List<OrderItem> getAllOrderDishes(){
        return orderItemDao.selectAll();
    }

    @Transactional
    public void update(OrderItem orderItem){
        orderItemDao.update(orderItem);
    }

    @Transactional
    public void add(OrderItem orderItem){
        orderItemDao.insert(orderItem);
    }

    @Transactional
    public List<OrderItem> getOrderItemByOrderId(int orderId){
        return orderItemDao.selectByOrderId(orderId);
    }

}
