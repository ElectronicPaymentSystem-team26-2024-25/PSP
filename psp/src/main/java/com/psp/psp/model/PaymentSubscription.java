package com.psp.psp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_subscriptions")
public class PaymentSubscription {

    @Column(nullable = false)
    private Long merchantId;

    @Column(nullable = false)
    private String paymentTypeName;

    public PaymentSubscription() {
    }

    public PaymentSubscription(Long merchantId, String paymentTypeName) {
        this.merchantId = merchantId;
        this.paymentTypeName = paymentTypeName;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }
}
