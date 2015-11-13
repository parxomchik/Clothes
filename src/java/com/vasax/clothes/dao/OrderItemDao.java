package com.vasax.clothes.dao;

import com.vasax.clothes.entities.OrderItem;
import com.vasax.clothes.entities.enums.Status;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by root on 24.11.14.
 */
@Named
public class OrderItemDao extends AbstractGenericDao<OrderItem>{

    public List<OrderItem> selectDishesByStatus(Status status){
        Query query = entityManager.
                createQuery("select orderD from OrderItem orderD where orderD.status = :status");
        query.setParameter("status", status);
        try {
            List<OrderItem> orderItems = (List<OrderItem>) query.getResultList();
            return orderItems;
        } catch (NoResultException e){
            return null;
        }
    }

    public List<OrderItem> selectByOrderId(int orderId){
        Query query = entityManager.
                createQuery("select orderD from OrderItem orderD where orderD.order.id = :orderId");
        query.setParameter("orderId", orderId);
        try {
            List<OrderItem> orderItems = (List<OrderItem>) query.getResultList();
            return orderItems;
        } catch (NoResultException e){
            return null;
        }
    }
}
