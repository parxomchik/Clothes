package com.vasax.clothes.managed;

import com.paypal.core.rest.PayPalRESTException;
import com.vasax.clothes.dao.ItemDao;
import com.vasax.clothes.entities.*;
import com.vasax.clothes.entities.enums.DeliveryType;
import com.vasax.clothes.entities.enums.PaymentType;
import com.vasax.clothes.entities.enums.ServiceType;
import com.vasax.clothes.entities.enums.Status;
import com.vasax.clothes.managed.pageFront.FilterPageBean;
import com.vasax.clothes.service.OrderService;
import com.vasax.clothes.service.OrderItemService;
import com.vasax.clothes.service.UserService;
import com.vasax.clothes.service.payment.PayPalService;
import com.vasax.clothes.util.ItemUtil;
import com.vasax.clothes.util.PaymentResultDto;
import org.springframework.context.annotation.Scope;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by vasax32 on 10.11.14.
 */
@Named
@Scope("session")
public class CartBean {

    private Map<Item, Integer> items = new HashMap<>();

    @Inject
    private ItemDao itemDao;

    @Inject
    private LoginBean loginBean;

    @Inject
    private OrderService orderService;

    @Inject
    private OrderItemService orderItemService;

    @Inject
    private PayPalService payPalService;

    @Inject
    private UserService userService;

    @Inject
    private ConstantsBean constantsBean;
    @Inject
    private ItemUtil itemUtil;

    public String addToCart(int itemId, int itemCount) {
        Item item = itemDao.selectById(itemId);
        if (items.containsKey(item)) {
            items.put(item, items.get(item) + itemCount);
        } else items.put(item, itemCount);
        //add message
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item added", item.getTitle());
        FacesContext.getCurrentInstance().addMessage("msgs", msg);
        return null;
    }

    public String removeFromCart(int itemId) {
//        Item item = itemDao.selectById(itemId);
////        if(!items.get(item).equals(1)){
////            items.put(item, items.get(item) -1);
////        } else
//        items.remove(item);

        for(Iterator<Map.Entry<Item, Integer>> it = items.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Item, Integer> entry = it.next();
            if(entry.getKey().getId() == itemId) {
                it.remove();
            }
        }

        return null;
    }


    public List<Item> getOnlyItems() {
        return new ArrayList<>(items.keySet());
    }

    public int getItemsCount(Item item) {
        return items.get(item);
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }

    public PaymentResultDto buy(int userId, Order order) throws PayPalRESTException, IOException {
        if (items.size() != 0) {
            //make payment
            String prefix = constantsBean.getPrefix();
            PaymentResultDto paymentResultDto = null;
            switch (order.getPaymentType()) {
                case payPal:
                    paymentResultDto = payPalService.doPayment(getApplicationUri() + ((!prefix.isEmpty()) ? "/" : "") + prefix, items);
                    order.setPaymentId(paymentResultDto.paymentId);
                    break;
                case privateBank:
                    paymentResultDto = null;
                    break; // here will we privat service
            }

            Status status = Status.toPayment;
            //Order order = new Order();

            order.setStatus(status);
//            order.setDeliveryType(deliveryType);
//            if(deliveryType == DeliveryType.service)
//                order.setServiceTitle(serviceType);
            order.setUser(userService.getById(userId));
            order.setTotal(calculateTotalPrice());
            order = orderService.add(order);
            for (Item item : items.keySet()) {
                int count = items.get(item);
                OrderItem orderItem = new OrderItem();
                orderItem.setCount(count);
                orderItem.setItem(item);
                orderItem.setStatus(status);
                orderItem.setOrder(order);
                double price = item.getPrice();
                orderItem.setPrice(price);
                orderItemService.add(orderItem);
            }
            items.clear();

            if(paymentResultDto == null){
                paymentResultDto = new PaymentResultDto("", "", "");
                paymentResultDto.orderId = order.getId();
            }

            return paymentResultDto;

        }
        return null;
//        System.out.println("Buy was clicked");
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

    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (Item item : items.keySet()) {
            totalPrice += calculatePriceByItem(item);
        }
        //need to decrease by discount
//        if(loginBean.isLinked()){
//            int discount = loginBean.getUser().getDiscount();
//            if(discount > 0){
//                totalPrice -= discount * totalPrice / 100;
//            }
//        }
        return totalPrice;
    }

    public double calculatePriceByItem(Item item){
        int discountOfUser = 0;
        if(loginBean.isLinked()){
            discountOfUser = loginBean.getUser().getDiscount();
        }
        boolean sale = itemUtil.isSale(item.getSaleValue(), item.getSaleStart(), item.getSaleEnd());
        double price = item.getPackPrice();
        if(sale && item.getSaleValue() > discountOfUser)
            price -= item.getSaleValue() * price / 100;
        else
            price -= discountOfUser * price / 100;
        int count = items.get(item);
        return price * count;
    }

    public int getUserDiscount(){
        if(loginBean.isLinked()){
            return loginBean.getUser().getDiscount();
        }
        return 0;
    }

    public double calculateTotalPriceWithoutDiscount(){
        double totalPrice = 0;
        for (Item item : items.keySet()) {
            int count = items.get(item);
            totalPrice += item.getPackPrice() * count;
        }
        return totalPrice;
    }

    public int getSale(){
        int userDiscount = 0;
        if(loginBean.isLinked()){
            userDiscount = loginBean.getUser().getDiscount();
        }

        for (Item item : items.keySet()) {
            if(itemUtil.isSale(item.getSaleValue(), item.getSaleStart(), item.getSaleEnd()))
                if(item.getSaleValue() > userDiscount)
                    userDiscount = item.getSaleValue();
        }
        return userDiscount;
    }
}
