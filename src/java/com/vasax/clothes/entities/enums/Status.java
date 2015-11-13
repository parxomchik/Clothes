package com.vasax.clothes.entities.enums;


/**
 * Created by root on 07.10.14.
 */
public enum Status {
    toPayment, //when user is not yet payed this order
    ordered, // when user just pressed buy button in cart
    toDelivery, //when manager approve order
    done,
    cooking,
    delivering,
    delivered
}
