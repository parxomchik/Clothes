package com.vasax.clothes.entities.enums;

/**
 * Created by Vasax on 12.05.2015.
 */
public class EnumConverter {
    public static String convert(DeliveryType deliveryType){
        switch (deliveryType){
            case newPostStorageDoor: return "Нова почта склад - двери";
            case newPostStorageStorage: return "Нова почта склад - склад";
            case AnotherDeliveryService: return "Сторонними службами доставки";
            case courierInKiev: return "Курьером по Киеву";
            default: return "Unknown";
        }
    }

    public static String convert(PaymentType paymentType){
        switch (paymentType){
            case payPal: return "Pay Pal";
            case privateBank: return "Приват Банк";
            case cashOnDelivery: return "Оплата при получении (наложеный платеж)";
            default: return "Unknown";
        }
    }
}
