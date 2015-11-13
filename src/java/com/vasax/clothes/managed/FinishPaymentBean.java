package com.vasax.clothes.managed;

import com.paypal.core.rest.PayPalRESTException;
import com.vasax.clothes.service.payment.PayPalService;
import org.springframework.context.annotation.Scope;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.net.URI;

/**
 * Created by vasax32 on 31.03.15.
 */
@Named
@Scope("request")
public class FinishPaymentBean {
    private String paymentId;
    private String token;
    private String PayerID;

    @Inject
    private PayPalService payPalService;

    public void init() throws PayPalRESTException {
        payPalService.finishPayment(paymentId, PayerID);

        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "index.xhtml");
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPayerID() {
        return PayerID;
    }

    public void setPayerID(String payerID) {
        PayerID = payerID;
    }
}
