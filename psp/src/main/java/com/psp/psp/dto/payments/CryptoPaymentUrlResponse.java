// src/main/java/com/psp/psp/dto/payments/CryptoPaymentUrlResponse.java
package com.psp.psp.dto.payments;

public class CryptoPaymentUrlResponse {
    private String paymentId;
    private String paymentUrl;

    public CryptoPaymentUrlResponse() { }

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getPaymentUrl() { return paymentUrl; }
    public void setPaymentUrl(String paymentUrl) { this.paymentUrl = paymentUrl; }
}
