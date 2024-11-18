package com.psp.psp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_subscriptions")
public class PaymentSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long merchantId;

    @Column(nullable = false)
    private String paymentTypeName;

    public PaymentSubscription() {
    }

    public PaymentSubscription(Long id, Long merchantId, String paymentTypeName) {
        this.id = id;
        this.merchantId = merchantId;
        this.paymentTypeName = paymentTypeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
