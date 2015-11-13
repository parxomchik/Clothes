package com.vasax.clothes.dao;

import com.vasax.clothes.entities.User;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by root on 24.11.14.
 */
@Named
public class UserDao extends AbstractGenericDao<User>{

    public User selectByLoginPassCustomer(String login, String pass){
        Query query = entityManager.
                createQuery("select user from User user where user.email = :email and user.pass = :pass and user.role = 'CUSTOMER' and user.active = true");
        query.setParameter("email", StringUtils.lowerCase(login));
        query.setParameter("pass", StringUtils.lowerCase(pass));
        try {
            User user = (User) query.getSingleResult();
            return user;
        } catch (NoResultException e){
            //pair is wrong
            return null;
        }
    }

    public User selectByLogin(String login){
        Query query = entityManager.
                createQuery("select user from User user where user.email = :email and user.active = true");
        query.setParameter("email", StringUtils.lowerCase(login));
        try {
            User user = (User) query.getSingleResult();
            return user;
        } catch (NoResultException e){
            return null;
        }
    }

    public User selectByLoginAdmin(String login){
        Query query = entityManager.
                createQuery("select user from User user where user.email = :email and user.role <> 'CUSTOMER' and user.active = true");
        query.setParameter("email", StringUtils.lowerCase(login));
        try {
            User user = (User) query.getSingleResult();
            return user;
        } catch (NoResultException e){
            return null;
        }
    }

    public List<User> selectAllCustomers() {
        Query query = entityManager.
                createQuery("select user from User user where user.role = 'CUSTOMER'");
            return query.getResultList();
    }

    public List<User> selectAllSystemUsers() {
        Query query = entityManager.
                createQuery("select user from User user where user.role <> 'CUSTOMER' and user.role <> 'SUPERUSER'");
        return query.getResultList();
    }

    public List<User> selectActiveManagers() {
        TypedQuery<User> query = entityManager.
                createQuery("select user from User user where user.role = 'MANAGER' and user.active = true", User.class);
        return query.getResultList();
    }

    public User selectCustomerByLogin(String email) {
        TypedQuery<User> query = entityManager.
                createQuery("select user from User user where user.role = 'CUSTOMER' and user.email = :email", User.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
