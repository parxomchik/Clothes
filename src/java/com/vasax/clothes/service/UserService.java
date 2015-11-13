package com.vasax.clothes.service;

import com.vasax.clothes.dao.PasswordResetTokenDao;
import com.vasax.clothes.entities.PasswordResetToken;
import com.vasax.clothes.entities.enums.Role;
import com.vasax.clothes.entities.User;
import com.vasax.clothes.dao.UserDao;
import com.vasax.clothes.util.PassEncoder;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by root on 24.11.14.
 */
@Named
public class UserService implements Serializable{

    @Inject
    UserDao userDao;

    @Inject
    private PasswordResetTokenDao resetTokenDao;

     @Inject
     private PassEncoder passEncoder;

    @Transactional
    public User getByLoginPassCustomer(String login, String pass){
        if(login == null || pass == null)
            return  null;
        return userDao.selectByLoginPassCustomer(login, pass);
    }

     @Transactional
     public User save(User user) {
         try {
             return userDao.insert(user);
         } catch (EntityExistsException e){
             return null;
         }
     }

     @Transactional
     public User update(User user){
         User oldUser = userDao.selectById(user.getId());
         if(oldUser != null && !oldUser.getPass().equals(user.getPass())){
             user.setPass(passEncoder.encode(user.getPass()));
         }
         return userDao.update(user);
     }

     @Transactional
     public User getByEmail(String email){
        return userDao.selectByLogin(email);
     }

    @Transactional
    public User getCustomerByEmail(String email){
        return userDao.selectCustomerByLogin(email);
    }

     @Transactional
     public List<User> getCustomers(){
         return userDao.selectAllCustomers();
     }

     @Transactional
     public List<User> getSystemUsers(){
         return userDao.selectAllSystemUsers();
     }

     @Transactional
     public void updateAll(List<User> customers) {
         for (User user : customers){
             update(user);
         }
     }

     @Transactional
     public void checkSuperuser(){
         User user = userDao.selectByLogin("superuser");
         if(user == null){
             user = new User("superuser", "superuser", "", "", passEncoder.encode("superuser"), Role.SUPERUSER, "", "", 0);
             user.setActive(true);
             save(user);
         }
     }

    @Transactional
    public User getById(int userId) {
        return userDao.selectById(userId);
    }

    @Transactional
    public User changePassword(User user, String newPassword) {
        user.setPass(passEncoder.encode(newPassword));
//        PasswordResetToken resetToken = resetTokenDao.findByUser(user);
//        if (resetToken != null) {
//            resetTokenDao.deleteToken(resetToken);
//        }

        return userDao.update(user);
    }

    @Transactional
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken resetToken = resetTokenDao.findByUser(user);
        if (resetToken ==null) {
            resetToken = new PasswordResetToken(token,user);
            resetTokenDao.insert(resetToken);
        } else {
            resetToken.updateToken(token);
            resetTokenDao.update(resetToken);
        }

    }

    @Transactional(readOnly = true)
    public PasswordResetToken getPasswordResetToken(String resetToken ) {
        return resetTokenDao.findByToken(resetToken);
    }

    @Transactional
    public List<User> getActiveManagers() {
        return userDao.selectActiveManagers();
    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {

        // Declare a null Spring User
        UserDetails user = null;

        try {

            // Search database for a user that matches the specified username
            // You can provide a custom DAO to access your persistence layer
            // Or use JDBC to access your database
            // DbUser is our custom domain user. This is not the same as Spring's User
            com.vasax.clothes.entities.User dbUser = userDao.selectByLogin(username);

            // Populate the Spring User object with details from the dbUser
            // Here we just pass the username, password, and access level
            // getAuthorities() will translate the access level to the correct role type

            user =  new org.springframework.security.core.userdetails.User(
                    dbUser.getEmail(),
                    dbUser.getPass().toLowerCase(),
                    true,
                    true,
                    true,
                    true,
                    getAuthorities(dbUser.getRole().ordinal()) );

        } catch (Exception e) {
            throw new UsernameNotFoundException("Error in retrieving user");
        }

        // Return user to Spring for processing.
        // Take note we're not the one evaluating whether this user is authenticated or valid
        // We just merely retrieve a user that matches the specified username
        return user;
    }

    public Collection<GrantedAuthority> getAuthorities(Integer access) {
        // Create a list of grants for this user
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

        // All users are granted with ROLE_USER access
        // Therefore this user gets a ROLE_USER by default
//			logger.debug("Grant ROLE_USER to this user");
//			authList.add(new GrantedAuthorityImpl("ROLE_USER"));
//
//			// Check if this user has admin access
//			// We interpret Integer(1) as an admin user
//			if ( access.compareTo(1) == 0) {
//				// User has admin access
//				logger.debug("Grant ROLE_ADMIN to this user");
//				authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
//			}
//
//			// Return list of granted authorities

        Role role = Role.values()[access];
        if(role != null)
            authList.add(new GrantedAuthorityImpl(role.name()));

        return authList;
    }
}
