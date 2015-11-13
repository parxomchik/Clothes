package com.vasax.clothes.dao;

import com.vasax.clothes.entities.PasswordResetToken;
import com.vasax.clothes.entities.User;

import javax.inject.Named;
import javax.persistence.NoResultException;


/**
 * Created by Владислав on 15.05.2015.
 */
@Named
public class PasswordResetTokenDao extends AbstractGenericDao<PasswordResetToken> {

    public PasswordResetToken findByToken(String token) {
        try {
            return (PasswordResetToken) entityManager.createQuery("Select resetToken from PasswordResetToken resetToken where resetToken.token=:tokenParam")
                    .setParameter("tokenParam", token).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public PasswordResetToken findByUser(User user) {
        try {
            return (PasswordResetToken) entityManager.createQuery("Select resetToken from PasswordResetToken resetToken where resetToken.user=:userParam")
                    .setParameter("userParam", user).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void deleteToken(PasswordResetToken token) {
        entityManager.createQuery("delete from PasswordResetToken resetToken where resettoken.id=:tokenId").setParameter("tokenId",token.getId());
    }
}
