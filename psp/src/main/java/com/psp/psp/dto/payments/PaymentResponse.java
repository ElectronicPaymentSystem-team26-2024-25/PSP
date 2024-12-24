package com.psp.psp.dto.payments;

public class PaymentResponse {
    private String paymentId;
    private String paymentUrl;
    public PaymentResponse(){}
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }
}
