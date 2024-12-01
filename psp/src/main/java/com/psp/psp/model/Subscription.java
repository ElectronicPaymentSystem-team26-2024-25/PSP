package com.psp.psp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "merchant_id")
    private Long merchantId;

    @Column(nullable = false, name = "payment_method_id")
    private Long paymentMethodId;

    @Column(name = "is_active")
    private boolean isActive;

    public Subscription() {
    }

    public Subscription(Long merchantId, Long paymentMethodId) {
        this.merchantId = merchantId;
        this.paymentMethodId = paymentMethodId;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
