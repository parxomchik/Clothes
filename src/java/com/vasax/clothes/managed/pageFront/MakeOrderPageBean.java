package com.vasax.clothes.managed.pageFront;

import com.paypal.core.rest.PayPalRESTException;
import com.vasax.clothes.entities.Order;
import com.vasax.clothes.entities.enums.DeliveryType;
import com.vasax.clothes.entities.enums.PaymentType;
import com.vasax.clothes.entities.enums.ServiceType;
import com.vasax.clothes.entities.User;
import com.vasax.clothes.managed.CartBean;
import com.vasax.clothes.managed.LoginBean;
import com.vasax.clothes.service.EmailService;
import com.vasax.clothes.service.UserService;
import com.vasax.clothes.util.PaymentResultDto;
import org.springframework.context.annotation.Scope;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by vasax32 on 28.04.15.
 */
@Named
@Scope("view")
public class MakeOrderPageBean {
    private User user;
    //private DeliveryType deliveryType;
    private ServiceType serviceType = ServiceType.newPost;
//    private PaymentType paymentType = PaymentType.cashOnDelivery;
    private boolean orderPrepared = false;
    private boolean orderMade = false;
    private Order order;

    @Inject
    private LoginBean loginBean;
    @Inject
    private CartBean cartBean;
    @Inject
    private UserService userService;
    @Inject
    private EmailService emailService;

//    @PostConstruct
    public void init(){
        if(loginBean.isLinked()){
            //user has log in
            this.user = loginBean.getUser();
        } else {
            //we need to create anonymous user
            user = new User();
        }
        order = new Order();
        order.setPaymentType(PaymentType.cashOnDelivery);
    }

    public void prepareOrder() {
        orderPrepared = true;
//        return "makeOrder";
    }

    public void editOrder() {
        orderPrepared = false;
//        return "makeOrder";
    }

    public void payOrder() throws PayPalRESTException, IOException {
        //registering order and redirect  to payment service
        if (!(loginBean.isLinked())) {
            user.setActive(false);
        }
        user = userService.update(user);
        if (order.getDeliveryType() == DeliveryType.courierInKiev) {
            order.setAddressOrStorageNum(user.getCountry()+", "+user.getCity()+", " + user.getStreet()+ ", " + user.getHouse() + ", кв. "+user.getApartment());
        }
        PaymentResultDto paymentResultDto = cartBean.buy(user.getId(), order);
        if(!paymentResultDto.approvalUrl.isEmpty()) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(paymentResultDto.approvalUrl);
        }

        orderMade = true;
        emailService.sendManagerNotificationByOrderId(paymentResultDto.orderId, getApplicationUri());
//        return null;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public boolean isOrderPrepared() {
        return orderPrepared;
    }

    public void setOrderPrepared(boolean orderPrepared) {
        this.orderPrepared = orderPrepared;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean isOrderMade() {
        return orderMade;
    }

    public void setOrderMade(boolean orderMade) {
        this.orderMade = orderMade;
    }
}
