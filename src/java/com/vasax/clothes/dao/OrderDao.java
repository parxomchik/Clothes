package com.vasax.clothes.dao;

import com.vasax.clothes.entities.Order;
import com.vasax.clothes.entities.enums.Status;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by root on 24.11.14.
 */
@Named
public class OrderDao extends AbstractGenericDao<Order>{

    public List<Order> selectOrdersByStatus(Status status){
        Query query = entityManager.
                createQuery("select orderX from Order orderX where orderX.status = :status");
        query.setParameter("status", status);
        try {
            return (List<Order>) query.getResultList();
        } catch (NoResultException e){
            return null;
        }
    }

    public List<Order> selectOrdersByStatusOrderedBuyDateAdd(Status status) {
        Query query = entityManager.
                createQuery("select orderX from Order orderX where orderX.status = :status order by orderX.dateAdd desc ");
        query.setParameter("status", status);
        try {
            return (List<Order>) query.getResultList();
        } catch (NoResultException e){
            return null;
        }
    }

    public List<Order> selectPayedOROrdersByStatusOrderedBuyDateAdd() {
//        Query query = entityManager.
//                createQuery("select orderX from Order orderX where orderX.status = 'ordered' or" +
//                        " (orderX.status = 'toPayment' and orderX.paymentType = 'cashOnDelivery') order by orderX.dateAdd desc ");
        Query query = entityManager.
                createQuery("select orderX from Order orderX where orderX.status = 'ordered' or" +
                        " orderX.status = 'toPayment' order by orderX.dateAdd desc ");
        try {
            return (List<Order>) query.getResultList();
        } catch (NoResultException e){
            return null;
        }
    }

    public List<Order> selectOrdersByStatusToDeliverySortByDateToDelivery(Status status) {
        Query query = entityManager.
                createQuery("select orderX from Order orderX where orderX.status = :status order by orderX.dateDelivered desc ");
        query.setParameter("status", status);
        try {
            return (List<Order>) query.getResultList();
        } catch (NoResultException e){
            return null;
        }
    }

    public List<Order> selectHistoryOfOrdersByStatusDoneOrderedByDateDone(Status status) {
        Query query = entityManager.
                createQuery("select orderX from Order orderX where orderX.status = :status order by orderX.dateDone desc ");
        query.setParameter("status", status);
        try {
            return (List<Order>) query.getResultList();
        } catch (NoResultException e){
            return null;
        }
    }

    public Order selectByPaymentId(String paymentId) {
        Query query = entityManager.
                createQuery("select orderX from Order orderX where orderX.paymentId = :paymentId ");
        query.setParameter("paymentId", paymentId);
        try {
            return (Order)query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
