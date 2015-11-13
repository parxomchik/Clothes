package com.vasax.clothes.util;

/**
 * Created by root on 22.02.15.
 */
public class PaymentResultDto {
    public String approvalUrl;
    public String executeUrl;
    public String paymentId;
    public int orderId;

    public PaymentResultDto(String approvalUrl, String executeUrl, String paymentId) {
        this.approvalUrl = approvalUrl;
        this.executeUrl = executeUrl;
        this.paymentId = paymentId;
    }

    @Override
    public String toString() {
        return "PaymentResultDto{" +
                "approvalUrl='" + approvalUrl + '\'' +
                ", executeUrl='" + executeUrl + '\'' +
                ", paymentId='" + paymentId + '\'' +
                '}';
    }
}
