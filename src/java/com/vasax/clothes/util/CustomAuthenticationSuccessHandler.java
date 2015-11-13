package com.vasax.clothes.util;

import com.vasax.clothes.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by root on 14.12.14.
 */
@Named
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Inject
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)
            throws IOException, ServletException {
        //do some logic here if you want something to be done whenever
        //the user successfully logs in.

        HttpSession session = httpServletRequest.getSession();
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("username", authUser.getUsername());
        session.setAttribute("authorities", authentication.getAuthorities());

        //set our response to OK status
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        //since we have created our custom success handler, its up to us to where
        //we will redirect the user after successfully login

        String redirectUrl = null;

        String role = new ArrayList<>(authUser.getAuthorities()).get(0).getAuthority();
        switch (role){
            case "ADMINISTRATOR" : redirectUrl = "/app/admin/administrator/categoryEdit.xhtml"; break;
            case "MANAGER" : redirectUrl = "/app/admin/manager/order.xhtml"; break;
            case "DELIVERY" : redirectUrl = "/app/admin/delivery/index.xhtml"; break;
            case "SUPERUSER" : redirectUrl = "/app/admin/superuser/customerEdit.xhtml"; break;
        }

        httpServletResponse.sendRedirect(redirectUrl);
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
