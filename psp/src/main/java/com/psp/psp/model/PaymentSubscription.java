package com.psp.psp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_subscriptions")
public class PaymentSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "merchant_id")
    private Long merchantId;

    @Column(nullable = false, name = "payment_type_id")
    private Long paymentTypeId;

    public PaymentSubscription() {
    }

    public PaymentSubscription(Long id, Long merchantId, Long paymentTypeId) {
        this.id = id;
        this.merchantId = merchantId;
        this.paymentTypeId = paymentTypeId;
    }

    public Long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
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

}
