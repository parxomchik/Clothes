package com.vasax.clothes.service.payment;

import com.paypal.api.payments.*;
import com.paypal.api.payments.Item;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import com.vasax.clothes.entities.enums.Status;
import com.vasax.clothes.service.OrderService;
import com.vasax.clothes.util.PaymentResultDto;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by root on 15.02.15.
 */
@Named
public class PayPalService {

    @Inject
    private OrderService orderService;

    private OAuthTokenCredential tokenCredential;

    private String currency = "USD";

    public PayPalService() throws PayPalRESTException {
        InputStream is = this.getClass().getResourceAsStream("/sdk_config.properties");
        tokenCredential = Payment.initConfig(is);
    }

    public PaymentResultDto doPayment(String domain, Map<com.vasax.clothes.entities.Item, Integer> clothes) throws PayPalRESTException {

        double totalPrice = 0;
        List<Item> items = new ArrayList<>();
        for(com.vasax.clothes.entities.Item cloth : clothes.keySet()){
            Integer count = clothes.get(cloth);
            items.add(new Item(Integer.toString(count), cloth.getTitle(), priceF(cloth.getPrice()), currency));
            totalPrice += count * cloth.getPrice();
        }

        String accessToken = tokenCredential.getAccessToken();
        APIContext apiContext = new APIContext(accessToken);


        Payment payment = new Payment().setIntent("sale");

        payment.setPayer(
                new Payer("paypal"));

        payment.setRedirectUrls(
                new RedirectUrls().
                        setReturnUrl(domain + "/finishPayment.xhtml").
                        setCancelUrl(domain + "/"));


        Transaction transaction = new Transaction();
        transaction.setAmount(new Amount()
                .setTotal(priceF(totalPrice))
                .setCurrency(currency))
                .setDescription("Clothes payment")
        .setItemList(new ItemList().setItems(items));

        payment.setTransactions(Arrays.asList(transaction));

        Payment paymentCreated = payment.create(apiContext);

        String approvalUrl = paymentCreated.getLinks().get(1).getHref();
        String executeUrl = paymentCreated.getLinks().get(2).getHref();
        return new PaymentResultDto(approvalUrl, executeUrl, paymentCreated.getId());
    }

    private String priceF(double price){
        return String.format(Locale.US, "%.2f", price);
    }

    public boolean isApproved(String paymentId) throws PayPalRESTException {
        String accessToken = tokenCredential.getAccessToken();
        Payment payment = Payment.get(accessToken, paymentId);

        return payment.getState().equals("approved");
    }

    private void execute(String paymentId, String payerId) throws PayPalRESTException {
        String accessToken = tokenCredential.getAccessToken();
        Payment payment = Payment.get(accessToken, paymentId);
        APIContext apiContext = new APIContext(accessToken);
        payment.execute(apiContext, new PaymentExecution(payerId));
    }

    @Transactional
    public void finishPayment(String paymentId, String payerId) throws PayPalRESTException {
        execute(paymentId, payerId);
        if(isApproved(paymentId)) {
            com.vasax.clothes.entities.Order order = orderService.getByPaymentId(paymentId);
            order.setStatus(Status.ordered);
            orderService.update(order);
        }
    }

    private String getOriginFromDomain(String domain) {
        Pattern p = Pattern.compile(".*?([^.]+\\.[^.]+)");
        URI uri = null;
        try {
            uri = new URI(domain);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Matcher m = p.matcher(uri.getHost());
        if (m.matches()) {
            return m.group(1);
        } else return "";
    }
}
